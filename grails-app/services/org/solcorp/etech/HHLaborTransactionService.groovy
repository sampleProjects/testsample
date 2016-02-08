package org.solcorp.etech

import grails.converters.JSON
import grails.util.GrailsUtil

import java.math.BigDecimal;
import java.text.DateFormat
import java.text.SimpleDateFormat

import org.hibernate.Criteria
import org.springframework.transaction.annotation.Propagation
import grails.transaction.Transactional

class HHLaborTransactionService {
	
	def projectService
	def employeeService
	def HHProjectMasterService
	def sessionFactory
	def userService
	def grailsApplication
	
	def private formatDate(laborTxnJobStDate) {
			if(laborTxnJobStDate != null && laborTxnJobStDate != "" && laborTxnJobStDate?.toString()?.trim().length() > 0){
				String dateReceivedFromParams = laborTxnJobStDate?.toString()?.trim()
				DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy")
				DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				Date date = userDateFormat.parse(dateReceivedFromParams)
				laborTxnJobStDate = dateFormatNeeded.format(date)
			}else{
				def today = new Date()
				def yesterday = today - 7
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				laborTxnJobStDate = format1.format(yesterday)
			
				/*Calendar cal = Calendar.getInstance();
				//cal.add(Calendar.DATE, 1);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				cal.add(Calendar.MONTH, -1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				laborTxnJobStDate = format1.format(cal.getTime());*/
			}
			return laborTxnJobStDate.toString();
	}
	
	def getLaborTransactionRecordsForImport(laborTxnJobStDate) {
		def hhLaborTransactionCriteria = HHLaborTransaction.createCriteria()
		def hhLaborTransactionList = hhLaborTransactionCriteria.list {
			and {
				ge("transactionDate", formatDate(laborTxnJobStDate))
				eq("importStatus", Constants.HH_LABORTRANSACTIONIMPORTSTATUS_LOADED)
				ne("projectId", '00000')		// Avoid importing transactions with projectId = '00000'
				gt("hours", '0')
			}
			
			order("transactionDate","asc")
		}
		
		return hhLaborTransactionList
	}
	
	def syncPlannedLaborWithActualLabor() {
		log.info "syncPlannedLaborWithActualLabor @ HHLaborTransactionService - START"
		
		try {
			// Find Undefined Activity
			def undefinedActivity = LaborActivityCode.findByCode(Constants.DEFAULT_LABOR_ACTIVITY_CODE)
			
			// Find Actual Labor for all but Undefined Activity
			def actualLaborDetailCriteria = ProjectActualLaborDetail.createCriteria()
			def actualLaborDetailList = actualLaborDetailCriteria.list {
				createAlias("project", "proj")
				createAlias("laborActivityCode", "activityCode")
				createAlias("projectLabor", "projectActualLabor")
				
				projections {
					groupProperty ('proj.id')
					groupProperty ('activityCode.id')
					groupProperty ('projectActualLabor.id')
				}
				
				if(undefinedActivity) {
					and {
						//eq("proj.id", 25253.toLong())
						ne("laborActivityCode", undefinedActivity)
					}
				}
				
				order("proj.id","asc")
				order("activityCode.id","asc")
				order("projectActualLabor.id", "asc")
			}
			
			log.info "Actual Records To be Processed: " + actualLaborDetailList.size()
			
			def projectId = null
			def laborActivityCode = null
			def projectActualLaborId = null
			def projectInstance = null
			def loggedInUser = userService.getLoggedInUser()
			
			if(actualLaborDetailList) {
				actualLaborDetailList.eachWithIndex { it, idx ->
					
					projectId = it[0]
					laborActivityCode = it[1]
					projectActualLaborId = it[2]
					
					log.info "${idx} :: ${projectId} :: ${laborActivityCode} :: ${projectActualLaborId}"
					
					def laborActivityCodeInstnace = LaborActivityCode.findById(laborActivityCode)
					def projectActualLaborInstance = ProjectActualLabor.findById(projectActualLaborId)
					createPlannedLaborForActualLabor(projectId, laborActivityCodeInstnace, projectActualLaborInstance, idx+1, loggedInUser)
				}
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
		
		log.info "syncPlannedLaborWithActualLabor @ HHLaborTransactionService - END"
	}

	@Transactional	
	def createPlannedLaborForActualLabor(projectId, laborActivityCodeInstnace, projectActualLaborInstance, recordNumber, currentUser) {
		log.info "createPlannedLaborForActualLabor @ HHLaborTransactionService - START"
		
		def logString = "${recordNumber} :: ${projectId} :: ${laborActivityCodeInstnace.id} :: ${projectActualLaborInstance.id}"
		
		try {
			// Find Actual Labor
			def projectInstance = null
			
			// Iterate actual labor List
			def projectServiceDetailInstance = ProjectServiceDetail.findByActualLabor(projectActualLaborInstance)
			if(projectServiceDetailInstance) {
				
				// Check if planned labor is available
				def projectPlannedLabor = projectServiceDetailInstance.plannedLabor 
				if(! projectPlannedLabor) {
					
					// Planned Labor is not available
					// Create a new planned labor with defaults
					projectPlannedLabor = new ProjectLabor()
					projectPlannedLabor.laborDetails = []
					projectPlannedLabor.laborActivityDate = new Date()
					
					projectPlannedLabor.dateCreated = new Date()
					projectPlannedLabor.lastUpdated = new Date()
					projectPlannedLabor.createdBy = currentUser
					projectPlannedLabor.updatedBy = currentUser
					
					projectPlannedLabor.save(flush: true)
					
					
					// Assigned planned labor to service detail instance
					projectServiceDetailInstance.plannedLabor = projectPlannedLabor
					projectServiceDetailInstance.save(flush: true, failOnError: true)
				}
						
				projectPlannedLabor = projectServiceDetailInstance.plannedLabor
				
				// Assign blank laborDetails if not exist already
				if(projectPlannedLabor.laborDetails == null) {
					projectPlannedLabor.laborDetails = []
				}
				
				// Check if activity is already planned
				if(!projectPlannedLabor.laborDetails*.laborActivityCode.contains(laborActivityCodeInstnace)) {
					// Activity is not planned
					
					// Create projectLaborDetail instance with defaults
					ProjectLaborDetail projectLaborDetailInstance = new ProjectLaborDetail()
					projectLaborDetailInstance.laborActivityCode = laborActivityCodeInstnace
					projectLaborDetailInstance.hours = 0
					projectLaborDetailInstance.standardRate = laborActivityCodeInstnace.standardRate
					projectLaborDetailInstance.standardCost = 0
					projectLaborDetailInstance.overHead = laborActivityCodeInstnace.overHead
					projectLaborDetailInstance.overHeadCost = 0
					projectLaborDetailInstance.totalCost = 0
					projectLaborDetailInstance.billAmountTotal = 0
					projectLaborDetailInstance.billRate = 0
					projectLaborDetailInstance.isFromActualLabor = Boolean.TRUE
					
					projectPlannedLabor.addToLaborDetails(projectLaborDetailInstance)
					projectPlannedLabor.save(flush:true)
					
					logString = "Success :: Planned Labor Inserted :: " + logString 
				} else {
					// Activity is already planned
					logString = "Success :: Planned Labor Already Available :: " + logString
				}
			}
			
			log.info logString
			log.info "createPlannedLaborForActualLabor @ HHLaborTransactionService - END"
		} catch (Exception e) {
			logString = "Failure :: " + logString
			
			log.info logString
			log.info "createPlannedLaborForActualLabor @ HHLaborTransactionService - END"
			
			throw new RuntimeException(e)
		}
	}
	
	def importLaborTransactions(currentJobId, laborTxnJobStDate) {
		def recordsToBeProcessed = 0
		def recordsProcessed = 0
		def recordsInserted = 0
		def recordsUpdated = 0
		def recordsFailed = 0 
		
		def importLaborTransactionJobLineRecordList = []
		
		def projectsNotfound = []
		def customersNotfound = []
		def employeesNotfound = []
		def employeesWithNoDepartment = []
		def departmentsWithNoLaborActivityGroup = []
		def activityGroupsWithNoActivityCode = []
		def projectsAffected = []
		def employeesWithNoActivityCode = [] 
		
		def insertedRecordsList = []
		def updatedRecordsList = []
		def failedRecordsList = []
		
		
		def currentJob = JobRegister.read(currentJobId)
		
		//def newLaborTransactionList = HHLaborTransaction.findAllByImportStatusAndTransactionDate(Constants.HH_LABORTRANSACTIONIMPORTSTATUS_LOADED, [max: 1000, offset: 0] )
		//def newLaborTransactionList = HHLaborTransaction.findAllByTransactionId("90288846")
		def newLaborTransactionList = getLaborTransactionRecordsForImport(laborTxnJobStDate)
		recordsToBeProcessed = newLaborTransactionList ? newLaborTransactionList.size() : 0
		
		log.info "Labor Transactions to be imported: " + recordsToBeProcessed
		
		User systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		def undefinedActivity = LaborActivityCode.findByCode(Constants.DEFAULT_LABOR_ACTIVITY_CODE)
		
		log.info "System Job User found: " + systemJobUser
		
		def projectList = []
		def employeeList = []
		
		def defaultProjectProduct = ProjectProduct.findByCode(Constants.DEFAULT_PRODUCT)
		def defaultService = Service.findByCode(Constants.DEFAULT_SERVICE) 
		
		def projectMap = [:]
		def employeeMap = [:]
		
		for (HHLaborTransaction hhLaborTransactionInstance : newLaborTransactionList) {

			def logString = "SourceTransactionId: " + hhLaborTransactionInstance.transactionId

			if(grailsApplication.config.shouldJobrun) {
				try {
					if(new BigDecimal(hhLaborTransactionInstance.hours) != 0 && hhLaborTransactionInstance.projectId != '00000') {
						def resultMap = processTransactionRecord(hhLaborTransactionInstance.transactionId, recordsProcessed, projectMap, employeeMap, employeesWithNoDepartment,
								recordsFailed, failedRecordsList, activityGroupsWithNoActivityCode, employeesWithNoActivityCode, departmentsWithNoLaborActivityGroup,
								currentJob, systemJobUser, employeesNotfound, projectsNotfound, defaultProjectProduct, defaultService, projectsAffected, recordsInserted, insertedRecordsList, undefinedActivity)

						recordsProcessed = resultMap.recordsProcessed
						recordsFailed = resultMap.recordsFailed
						recordsInserted = resultMap.recordsInserted
					} else {
						recordsProcessed++
						logString = "Skipped: " + logString + " :: Hours = 0 OR ProjectId = '00000'"

						log.info "Rec#: ${recordsProcessed} :: " + logString
					}
				} catch (Exception e) {
					e.printStackTrace()
				}
			} else {
				log.info "Emergency Stop. ImportLaborTransactionsJob is terminated gracefully."
				break;
			}
		}
		
		// Insert LaborTransactionJobLineRecods
		// ImportLaborTransactionJobLineRecord.saveAll(importLaborTransactionJobLineRecordList)
		
		// Create import job summary and save along with the job
		def returnObject = getImportLaborTransactionSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsUpdated, recordsFailed, insertedRecordsList,
			updatedRecordsList, failedRecordsList, projectsNotfound, customersNotfound, employeesNotfound, 
			employeesWithNoDepartment, departmentsWithNoLaborActivityGroup, activityGroupsWithNoActivityCode, employeesWithNoActivityCode, projectsAffected) 
		
		
		return returnObject
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def processTransactionRecord(sourceTransactionId, recordsProcessed, projectMap, employeeMap, employeesWithNoDepartment, 
				recordsFailed, failedRecordsList, activityGroupsWithNoActivityCode, employeesWithNoActivityCode, departmentsWithNoLaborActivityGroup, 
				currentJob, systemJobUser, employeesNotfound, projectsNotfound, defaultProjectProduct, defaultService, 
				projectsAffected, recordsInserted, insertedRecordsList, undefinedActivity) {
		
		def hhLaborTransactionInstance = HHLaborTransaction.findByTransactionId(sourceTransactionId)
				
		recordsProcessed++
		
		def projectInstance = null
		def employeeInstance = null
		def customerInstance = null
		def laborActivityCode = null
		def projectActualLaborDetailInstance = null
		
		def logString = "SourceTransactionId: " + hhLaborTransactionInstance.transactionId
		
		try {
			// Check if project exists in the system
			
			// Commented as we don't need manipulated comparison for project code - START
			/*projectList = projectService.getProjectByCode(hhLaborTransactionInstance.projectId, hhLaborTransactionInstance.projectId.isNumber())
			
			if(projectList.size() == 1) {
				projectInstance = projectList.get(0)
			}*/
			// Commented as we don't need manipulated comparison for project code - END
			
			if (projectMap.containsKey(hhLaborTransactionInstance.projectId)) {
				projectInstance = projectMap.get(hhLaborTransactionInstance.projectId)
				projectInstance.attach()
			}  else {
				projectInstance = projectService.findByCode(hhLaborTransactionInstance.projectId)
			}
			
			// Check in ps_hhpc_prjcust_vw for project if not found in the system
			/*if(!projectInstance) {
				def projectImportResultMap = HHProjectMasterService.importHHProjects(currentJob.id, hhLaborTransactionInstance.projectId)
				
				if(projectImportResultMap) {
					projectInstance = projectImportResultMap.projectInstance
					if(projectInstance) {
						log.info "${projectInstance.code} Imported :: " + logString
					}
				}
			}*/
			
			if(projectInstance) {
				// Project found
				// Populate in Map
				projectMap.put(hhLaborTransactionInstance.projectId, projectInstance)
				
				// Set customer Instance
				customerInstance = projectInstance.customer
				
				/*// Check if customer exists in the system
				customerInstance = Customer.findByCode(hhLaborTransactionInstance.projectCustRevId)
				if(customerInstance) {
					// Customer found*/
										
					// Check Employee in the system
				
					// Commented as we don't need manipulated comparison for employee code - START
					/*employeeList = employeeService.getEmployeeByCode(hhLaborTransactionInstance.employeeId, hhLaborTransactionInstance.employeeId.isNumber())
					if(employeeList.size() == 1) {
						employeeInstance = employeeList.get(0)
					}*/
					// Commented as we don't need manipulated comparison for employee code - END
				
					if(employeeMap.containsKey(hhLaborTransactionInstance.employeeId)) {
						employeeInstance = employeeMap.get(hhLaborTransactionInstance.employeeId)
					} else {
						employeeInstance = employeeService.findByCode(hhLaborTransactionInstance.employeeId)
					}
					
					if(employeeInstance) {
						// Employee found
						// Populate in Map
						employeeMap.put(hhLaborTransactionInstance.employeeId, employeeInstance)
						
						// Check if Employee has Labor Activity Code
						if(! employeeInstance.laborActivityCode) {
							// Employee doesn't have laborActivityCode
							
							logString = "Failure: " + logString + " :: No Activity found :: EmployeeCode: ${employeeInstance.code}"
							
							if(! employeesWithNoActivityCode.contains(employeeInstance?.code)) {
								employeesWithNoActivityCode.add(employeeInstance?.code)
							}

							recordsFailed++

							ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.LABOR_ACTIVITY_NOT_FOUND, systemJobUser).save(flush: true)
							//println "Error: failedLineRecord: " + failedLineRecord.errors
							//importLaborTransactionJobLineRecordList.add(failedLineRecord)
							
							ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
							failedRecordsList.add(failedTransactionRecord)
																	
							// Commented as Activity Code is now being fetched from the employee directly - START
							/*// Time to find out the default labor activity code based on the department
							if(! employeeInstance.payDept) {
								// This employee doesn't have payDept.
								// We are helpless now. This record will go in as a failedLaborTransactionRecord

								logString = "Failure: " + logString + " :: No PayDept found :: EmployeeCode: ${employeeInstance.code}"

								if(! employeesWithNoDepartment.contains(employeeInstance.code)) {
									employeesWithNoDepartment.add(employeeInstance.code)
								}

								recordsFailed++

								ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.PAY_DEPT_NOT_FOUND, systemJobUser).save(flush: true)
								//println "Error: failedLineRecord: " + failedLineRecord.errors
								//importLaborTransactionJobLineRecordList.add(failedLineRecord)
								
								ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
								failedRecordsList.add(failedTransactionRecord)
							} else {
								// This employee has payDept.

								// Check if payDept has ActivityGroup
								def laborActivityGroup = employeeInstance?.payDept?.laborActivityGroup
								if(laborActivityGroup) {

									// Check if the laborActivityCode is available
									if(laborActivityGroup.laborActivityCodes?.size() > 0) {

										// TODO: Implement a logic to find the first activity code
										laborActivityCode = employeeInstance?.payDept?.laborActivityGroup?.laborActivityCodes.first()
										
										// Commented the check for Invalid Standard Rate - START
										if(laborActivityCode?.standardRate <= 1) {
											
											logString = "Failure: " + logString + " :: Invalid Standard Rate :: LaborActivityCode: ${laborActivityCode.code}"
										
											recordsFailed++
											
											laborActivityCode = null
											
											ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.INVALID_STD_RATE, systemJobUser).save(flush: true)
																
											ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
											failedRecordsList.add(failedTransactionRecord)
										}
										// Commented the check for Invalid Standard Rate - END
									} else {
										// This Labor Activity Group has not activity code

										logString = "Failure: " + logString + " :: No Activity found :: LaborActivityGroupCode: ${laborActivityGroup.code}"

										if(! activityGroupsWithNoActivityCode.contains(employeeInstance?.payDept?.laborActivityGroup?.code)) {
											activityGroupsWithNoActivityCode.add(employeeInstance?.payDept?.laborActivityGroup?.code)
										}

										recordsFailed++

										ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.LABOR_ACTIVITY_NOT_FOUND, systemJobUser).save(flush: true)
										//println "Error: failedLineRecord: " + failedLineRecord.errors
										//importLaborTransactionJobLineRecordList.add(failedLineRecord)
										
										ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
										failedRecordsList.add(failedTransactionRecord)
									}
								} else {
									// This payDept has not Activity Group

									logString = "Failure: " + logString + " :: No Activity Group found :: DepartmentCode: ${employeeInstance?.payDept.code}"

									if(! departmentsWithNoLaborActivityGroup.contains(employeeInstance?.payDept.code)) {
										departmentsWithNoLaborActivityGroup.add(employeeInstance?.payDept.code)
									}

									recordsFailed++

									ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.LABOR_ACT_DEPT_NOT_FOUND, systemJobUser).save(flush: true)
									//println "Error: failedLineRecord: " + failedLineRecord.errors
									//importLaborTransactionJobLineRecordList.add(failedLineRecord)
									
									ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
									failedRecordsList.add(failedTransactionRecord)
								}

							}*/
							// Commented as Activity Code is now being fetched from the employee directly - END

						} else {
							// Employee has laborActivityCode
							laborActivityCode = employeeInstance.laborActivityCode
							
							// Skip Traxns where employee have undefined activity
							if(laborActivityCode?.id == undefinedActivity?.id) {
								logString = "Failure: " + logString + " :: Undefined Activity :: EmployeeCode: ${employeeInstance.code} :: LaborActivityCode: ${laborActivityCode.code}"
								
								recordsFailed++
								
								laborActivityCode = null
								
								ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.UNDEFINED_LABOR_ACTIVITY, systemJobUser).save(flush: true)
													
								ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
								failedRecordsList.add(failedTransactionRecord)
							}
							
							// Commented the check for Invalid Standard Rate - START
							/*if(laborActivityCode?.standardRate <= 1) {
								
								logString = "Failure: " + logString + " :: Invalid Standard Rate :: LaborActivityCode: ${laborActivityCode.code}"
								
								recordsFailed++
									
								laborActivityCode = null
								
								ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.INVALID_STD_RATE, systemJobUser).save(flush: true)
													
								ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
								failedRecordsList.add(failedTransactionRecord)
							}*/
							// Commented the check for Invalid Standard Rate - END
						}

					} else {
						// Employee not found

						logString = "Failure: " + logString + " :: No Employee found :: EmployeeCode: ${hhLaborTransactionInstance.employeeId}"

						if(! employeesNotfound.contains(hhLaborTransactionInstance.employeeId)) {
							employeesNotfound.add(hhLaborTransactionInstance.employeeId)
						}

						recordsFailed++

						ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.EMPLOYEE_NOT_FOUND, systemJobUser).save(flush: true)
						//println "Error: failedLineRecord: " + failedLineRecord.errors
						//importLaborTransactionJobLineRecordList.add(failedLineRecord)
						
						ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
						failedRecordsList.add(failedTransactionRecord)
					}
				/*} else {
					// Customer not found
				
					logString = "Failure: " + logString + " :: No Customer found :: CustomerCode: ${hhLaborTransactionInstance.projectCustRevId}"
					
					if(! customersNotfound.contains(hhLaborTransactionInstance.projectCustRevId)) {
						customersNotfound.add(hhLaborTransactionInstance.projectCustRevId)
					}

					recordsFailed++

					ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
					failedRecordsList.add(failedTransactionRecord)
				}*/
			} else {
				// Project not found
				
				logString = "Failure: " + logString + " :: No Project found :: ProjectCode: ${hhLaborTransactionInstance.projectId}"
			
				if(! projectsNotfound.contains(hhLaborTransactionInstance.projectId)) {
					projectsNotfound.add(hhLaborTransactionInstance.projectId)
					
					// Populate HHMissingProject
					def hhMissingProject = HHMissingProject.findBySourceProjectId(hhLaborTransactionInstance.projectId)
					if(! hhMissingProject) {
						hhMissingProject = new HHMissingProject(hhLaborTransactionInstance, null, logString, HHMissingProjectStatusType.NOT_FOUND_IN_HHPC, currentJob, systemJobUser).save(flush: true)
						log.info "No Project found :: ProjectCode: ${hhLaborTransactionInstance.projectId} Inserted in MissingProjects: ${hhMissingProject.id}"
					}
				}
			
				recordsFailed++
				
				ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, null, logString, ImportLaborTransactionStatusType.PROJECT_NOT_FOUND, systemJobUser).save(flush: true)
				//println "Error: failedLineRecord: " + failedLineRecord.errors
				//importLaborTransactionJobLineRecordList.add(failedLineRecord)
				
				ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, null, logString)
				failedRecordsList.add(failedTransactionRecord)
			}
			
			// Process Transactions with LaborActivityCode.operations == 'Y'
			if(laborActivityCode && laborActivityCode.operations != null && laborActivityCode.operations.toUpperCase() == 'Y') {
				
				def defaultProjectProductDetail = null
				// TODO: Check if project has projectProductDetails
				if(projectInstance.projectProductDetails) {
					// This project has project product details
					
					// Check if default product is added in details
					defaultProjectProductDetail = ProjectProductDetail.findByProjectAndProjectProduct(projectInstance, defaultProjectProduct)
				}
				
				if(! defaultProjectProductDetail) {
					// This project has either no details or no default project details.
					// Let's add default project details with defaultProjectProduct and defaultService
					
					defaultProjectProductDetail = new ProjectProductDetail(defaultProjectProduct, defaultService)
					projectInstance.addToProjectProductDetails(defaultProjectProductDetail)
					projectInstance.save(flush: true, failOnError: true)
					
					projectMap.put(projectInstance.code, projectInstance)
				}
				
				def defaultProjectServiceDetail = ProjectServiceDetail.findByProjectProductDetailAndService(defaultProjectProductDetail, defaultService)
				def actualLaborInstance = defaultProjectServiceDetail.actualLabor
				if(! actualLaborInstance) {
					
					actualLaborInstance = new ProjectActualLabor().save(flush: true, faileOnError: true)
					defaultProjectServiceDetail.actualLabor = actualLaborInstance
					defaultProjectServiceDetail.save(flush: true, failOnError: true)
				}
				
				projectActualLaborDetailInstance = new ProjectActualLaborDetail(hhLaborTransactionInstance, projectInstance, customerInstance, employeeInstance, laborActivityCode, systemJobUser)
				actualLaborInstance.addToLaborDetails(projectActualLaborDetailInstance)
				//actualLaborInstance.total = actualLaborInstance.total.add(projectActualLaborDetailInstance.standardTotalCost)
				actualLaborInstance.save(flush: true, failOnError: true)
				
				// Mark the current history transaction as used.
				hhLaborTransactionInstance.importStatus = Constants.HH_LABORTRANSACTIONIMPORTSTATUS_IMPORTED
				hhLaborTransactionInstance.importStatusDate = new Date().format(Constants.DATETIME_FORMAT_YYYY_MM_DD)
				hhLaborTransactionInstance.save(failOnError: true)
				
				// Create Planned Labor for this actual transaction
				// Below mentioned method will check if this activity has already been planned 
				createPlannedLaborForActualLabor(projectInstance.id, laborActivityCode, actualLaborInstance, 1, systemJobUser)
				
				// Update lastTrx for project
				boolean updateLastTrx = false
				def oldDate = projectInstance?.lastTrx
				if(projectInstance?.lastTrx) {
					if(projectInstance?.lastTrx?.compareTo(projectActualLaborDetailInstance.transactionDate) < 0 ) {
						projectInstance.lastTrx = projectActualLaborDetailInstance.transactionDate
						updateLastTrx = true
					}
				} else {
					projectInstance.lastTrx = projectActualLaborDetailInstance.transactionDate
					updateLastTrx = true
				}
				
				if(updateLastTrx) {
					projectInstance.save(flush: true, failOnError: true)
					projectMap.put(projectInstance.code, projectInstance)
					
					log.info "Last Trx Updated :: ProjectCode: ${hhLaborTransactionInstance.projectId} :: oldDate: ${oldDate} :: newDate: ${projectInstance.lastTrx} :: actualLaborId: ${projectActualLaborDetailInstance.id}"
				}
				
				// Add project in projectsAffected
				if(! projectsAffected.contains(projectInstance.code)) {
					projectsAffected.add(projectInstance.code)
				}
				
				recordsInserted++
				
				logString = "Success: " + logString + ", actualLaborDetailId: " + projectActualLaborDetailInstance?.id
			
				ImportLaborTransactionJobLineRecord successLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, projectActualLaborDetailInstance?.id, logString, ImportLaborTransactionStatusType.SUCCESS, systemJobUser).save(flush: true)
				//importLaborTransactionJobLineRecordList.add(successLineRecord)
				
				ImportLaborTransactionJobRecordDTO insertedProjectRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, projectActualLaborDetailInstance?.id, logString)
				insertedRecordsList.add(insertedProjectRecord)
			} else {
				if(laborActivityCode != null) {
					if(laborActivityCode.operations == null) {
						logString = "Skipped: " + logString + " :: LaborActivityCode.Operations == 'NULL' :: LaborActivityCode: ${laborActivityCode.code}"
					} else if(laborActivityCode.operations.toUpperCase != 'Y') {
						logString = "Skipped: " + logString + " :: LaborActivityCode.Operations != 'Y' :: LaborActivityCode: ${laborActivityCode.code}"
					}
				}
			}
			
			log.info "Rec#: ${recordsProcessed} :: " + logString
			
			laborActivityCode = null
		} catch (Exception e) {
			
			logString = "Failure: " + logString + ", actualLaborDetailId: " + projectActualLaborDetailInstance?.id
			
			recordsFailed++
			
			ImportLaborTransactionJobLineRecord failedLineRecord = new ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, currentJob, projectActualLaborDetailInstance?.id, e.getCause(), ImportLaborTransactionStatusType.OTHER, systemJobUser).save(flush: true)
			//println "Error: failedLineRecord: " + failedLineRecord.errors
			//importLaborTransactionJobLineRecordList.add(failedLineRecord)
			
			ImportLaborTransactionJobRecordDTO failedTransactionRecord = new ImportLaborTransactionJobRecordDTO(hhLaborTransactionInstance?.transactionId, projectActualLaborDetailInstance?.id, e.getCause())
			failedRecordsList.add(failedTransactionRecord)
			
			log.error e
			
			log.info "Rec#: ${recordsProcessed} :: " + logString
			
			throw new RuntimeException(e)
		}
		
		return [recordsProcessed: recordsProcessed, recordsFailed: recordsFailed, recordsInserted: recordsInserted]
	}
	
	def getImportLaborTransactionSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsUpdated, recordsFailed, insertedRecordsList, 
		updatedRecordsList, failedRecordsList, projectsNotfound, customersNotfound, employeesNotfound, 
			employeesWithNoDepartment, departmentsWithNoLaborActivityGroup, activityGroupsWithNoActivityCode, employeesWithNoActivityCode, projectsAffected) {
		
		ImportLaborTransactionJobSummaryDTO summary = new ImportLaborTransactionJobSummaryDTO(recordsToBeProcessed, recordsProcessed, recordsInserted, 
			recordsUpdated, recordsFailed, insertedRecordsList, updatedRecordsList, failedRecordsList, projectsNotfound, customersNotfound, employeesNotfound, 
			employeesWithNoDepartment, departmentsWithNoLaborActivityGroup, activityGroupsWithNoActivityCode, employeesWithNoActivityCode, projectsAffected)
		
		return summary as JSON
	}
	
			
	def createNewTransaction() {
		sessionFactory.getCurrentSession().beginTransaction()
	}
	
	def endTransaction() {
		flushHibernateSession()
		sessionFactory.getCurrentSession().getTransaction().commit()
	}
	
	def flushHibernateSession() {
		def hibSession = sessionFactory.getCurrentSession()
		assert hibSession != null
		hibSession.flush()
		hibSession.clear()
	}
}