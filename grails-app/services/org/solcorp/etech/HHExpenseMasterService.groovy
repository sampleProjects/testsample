package org.solcorp.etech

import grails.converters.JSON
import grails.util.GrailsUtil

import java.math.BigDecimal;
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date;

import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

class HHExpenseMasterService {
	
	def projectService
	def employeeService
	def HHProjectMasterService
	def sessionFactory
	def userService
	def grailsApplication
	
	def syncPlannedExpenseWithActualExpense() {
		log.info "syncPlannedExpenseWithActualExpense @ HHExpenseMasterService - START"
		
		try {
			// Find Actual Expense
			def actualExpenseDetailCriteria = ProjectActualExpenseDetail.createCriteria()
			def actualExpenseDetailList = actualExpenseDetailCriteria.list {
				createAlias("project", "proj")
				createAlias("expense", "expense")
				createAlias("projectActualExpense", "projectActualExpense")
				
				projections {
					groupProperty ('proj.id')
					groupProperty ('expense.id')
					groupProperty ('projectActualExpense.id')
				}
				
				/*and {
					eq("proj.code", '0000008455')
				}*/
				
				order("proj.id","asc")
				order("expense.id","asc")
				order("projectActualExpense.id", "asc")
			}
			
			log.info "Actual Records To be Processed: " + actualExpenseDetailList.size()
			
			def projectId = null
			def expenseId = null
			def projectActualExpenseId = null
			def loggedInUser = userService.getLoggedInUser()
			
			if(actualExpenseDetailList) {
				actualExpenseDetailList.eachWithIndex { it, idx ->
					
					projectId = it[0]
					expenseId = it[1]
					projectActualExpenseId = it[2]
					
					log.info "Record: ${idx} :: ProjectId: ${projectId} :: ExpenseId: ${expenseId} :: ActualExpenseId: ${projectActualExpenseId}"
					
					def expenseInstance = Expense.findById(expenseId)
					def projectActualExpenseInstance = ProjectActualExpense.findById(projectActualExpenseId)
					
					createPlannedExpenseForActualExpense(projectId, expenseInstance, projectActualExpenseInstance, idx+1, loggedInUser)
				}
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
		
		log.info "syncPlannedExpenseWithActualExpense @ HHExpenseMasterService - END"
	}
	
	@Transactional
	def createPlannedExpenseForActualExpense(projectId, expenseInstance, projectActualExpenseInstance, recordNumber, currentUser) {
		log.info "createPlannedExpenseForActualExpense @ HHExpenseMasterService - START"
		
		def logString = "Record:  ${recordNumber} :: ProjectId: ${projectId} :: ExpenseId: ${expenseInstance.id} :: ActualExpenseId: ${projectActualExpenseInstance.id}"
		
		try {
			
			// Find appropriate service detail instance
			def projectServiceDetailInstance = ProjectServiceDetail.findByActualExpense(projectActualExpenseInstance)
			if(projectServiceDetailInstance) {
				
				// Check if planned expense is available
				def projectPlannedExpense = projectServiceDetailInstance.plannedExpense
				if(! projectPlannedExpense) {
					
					// Planned Expense is not available
					// Create a new planned expense with defaults
					projectPlannedExpense = new ProjectExpense()
					projectPlannedExpense.expenseDetails = []
					projectPlannedExpense.expenseDate = new Date()
					
					projectPlannedExpense.dateCreated = new Date()
					projectPlannedExpense.lastUpdated = new Date()
					projectPlannedExpense.createdBy = currentUser
					projectPlannedExpense.updatedBy = currentUser
					
					projectPlannedExpense.save(flush: true)
					
					
					// Assigned planned expense to service detail instance
					projectServiceDetailInstance.plannedExpense = projectPlannedExpense
					projectServiceDetailInstance.save(flush: true, failOnError: true)
				}
						
				projectPlannedExpense = projectServiceDetailInstance.plannedExpense
				
				// Assign blank expenseDetails if not exist already
				if(projectPlannedExpense.expenseDetails == null) {
					projectPlannedExpense.expenseDetails = []
				}
				
				// Check if expense is already planned
				if(!projectPlannedExpense.expenseDetails*.expenseCode.contains(expenseInstance)) {
					// expense is not planned
					
					// Create projectExpenseDetail instance with defaults
					ProjectExpenseDetail projectExpenseDetailInstance = new ProjectExpenseDetail()
					projectExpenseDetailInstance.expenseCode = expenseInstance
					projectExpenseDetailInstance.qty = 0
					projectExpenseDetailInstance.unitCost = expenseInstance.standardRate
					projectExpenseDetailInstance.totalCost = 0
					projectExpenseDetailInstance.isFromActualExpense = Boolean.TRUE
					
					projectPlannedExpense.addToExpenseDetails(projectExpenseDetailInstance)
					projectPlannedExpense.save(flush:true)
					
					logString = "Success :: Planned Expense Inserted :: " + logString
				} else {
					// Expense is already planned
					logString = "Success ::  Planned Expense Already Available :: " + logString
				}
			}
			
			log.info logString
			log.info "createPlannedExpenseForActualExpense @ HHExpenseMasterService - END"
		} catch (Exception e) {
			logString = "Failure :: " + logString
			
			log.info logString
			log.info "createPlannedExpenseForActualExpense @ HHExpenseMasterService - END"
			
			throw new RuntimeException(e)
		}
	}
	
	def private formatDate(startDate) {		
			if(startDate != null && startDate != "" && startDate?.toString()?.trim().length() > 0){
				String dateReceivedFromParams = startDate?.toString()?.trim()
				DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy")
				startDate = userDateFormat.parse(dateReceivedFromParams)				
			}else{
				
				def today = new Date()
				startDate = today - 7
			
				/*Calendar cal = Calendar.getInstance()				
				cal.set(Calendar.DAY_OF_MONTH, 1)
				cal.add(Calendar.MONTH, -1)
				cal.set(Calendar.HOUR_OF_DAY, 0)
				cal.set(Calendar.MINUTE, 0)
				cal.set(Calendar.SECOND, 0)
				startDate = cal.getTime()*/
			}
			return startDate
	}
	
	def getRecordsForImport(startDate) {
		def hhExpenseMasterCriteria = HHExpenseMaster.createCriteria()
		def hhExpenseRecordsList = hhExpenseMasterCriteria.list {
			projections {
				groupProperty('businessUnit')			//0
				groupProperty('journalId')				//1
				groupProperty('journalDate')			//2
				groupProperty('unpostSeq')				//3
				groupProperty('fiscalYear')				//4
				groupProperty('accountingPeriod')		//5
				groupProperty('source')					//6
				groupProperty('jrnlHdrStatus')			//7
				groupProperty('postedDate')				//8
				groupProperty('descr')					//9
				groupProperty('currencyCd')				//10
				groupProperty('jrnlCreateDttm')			//11
				groupProperty('journalDateOrig')		//12
				groupProperty('journalLine')			//13
				groupProperty('account')				//14
				groupProperty('deptid')					//15
				groupProperty('operatingUnit')			//16
				groupProperty('product')				//17
				groupProperty('projectId')				//18
				groupProperty('monetaryAmount')			//19
				groupProperty('foreignCurrency')		//20
				groupProperty('foreignAmount')			//21
				groupProperty('voucherId')				//22
				groupProperty('invoiceId')				//23
				groupProperty('vendorId')				//24
				groupProperty('name1')					//25
				groupProperty('dttm_stamp')				//26
				groupProperty('descrshort')				//27
			}
			
			and {
				ge("postedDate", formatDate(startDate))
				eq("descrshort", Constants.HH_EXPENSEMASTERIMPORTSTATUS_LOADED)
				//'in'("projectId", ['0000007742', '0000008455', '0000009014'])				
			}
			
			order("postedDate", "asc")
		}
		return hhExpenseRecordsList
	}
	
	def importExpenseRecords(currentJobId, startDate) {
		def recordsToBeProcessed = 0
		def recordsProcessed = 0
		def recordsInserted = 0
		def recordsFailed = 0 
		
		def insertedRecordsList = []
		def failedRecordsList = []
		
		def projectsNotfound = []
		def expenseNotfound = []
		
		def currentJob = JobRegister.read(currentJobId)
		
		def expenseRecordsList = getRecordsForImport(startDate)
		recordsToBeProcessed = expenseRecordsList ? expenseRecordsList.size() : 0
		
		def glAccountNumberList = Expense.findAll().glAccountNumber
		def glAccountExpenseMap = [:]
		
		def defaultProjectProduct = ProjectProduct.findByCode(Constants.DEFAULT_PRODUCT)
		def defaultService = Service.findByCode(Constants.DEFAULT_SERVICE)
		
		log.info "Expense records to be processed: " + recordsToBeProcessed
		
		User systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		log.info "System Job User found: " + systemJobUser
		
		def projectMap = [:]
		
		for (HHExpenseMaster hhExpenseMsaterInstance : expenseRecordsList) {
			
			if(grailsApplication.config.shouldJobrun) {
				
				try {
					def resultMap = processExpenseRecord(hhExpenseMsaterInstance, projectMap, projectsNotfound, expenseNotfound, defaultProjectProduct,
							defaultService, recordsProcessed, recordsFailed, currentJob, systemJobUser, recordsInserted, insertedRecordsList,
							failedRecordsList, glAccountNumberList, glAccountExpenseMap)
	
					recordsProcessed = resultMap.recordsProcessed
					recordsFailed = resultMap.recordsFailed
					recordsInserted = resultMap.recordsInserted
					
				} catch (Exception e) {
					e.printStackTrace()
				}
			} else {
				log.info "Emergency Stop. ImportExpenseJob is terminated gracefully."
				break;
			}
		}
		
		log.info "Projects Not found: " + projectsNotfound
		log.info "Expenses Not found: " + expenseNotfound
		
		// Create import job summary and save along with the job
		def returnObject = getImportExpenseSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsFailed, insertedRecordsList,
				failedRecordsList, projectsNotfound, expenseNotfound)
		
		return returnObject
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def processExpenseRecord(hhExpenseMsaterInstance, projectMap, projectsNotfound, expenseNotfound, defaultProjectProduct, 
		defaultService, recordsProcessed, recordsFailed, currentJob, systemJobUser, recordsInserted, insertedRecordsList, failedRecordsList, 
		glAccountNumberList, glAccountExpenseMap) {
		
		recordsProcessed++
		
		def projectInstance = null
		def projectActualExpenseDetailInstance = null
		
		def logString = "SourceProjectId: ${hhExpenseMsaterInstance.projectId} :: JournalId: ${hhExpenseMsaterInstance.journalId} :: lineNo: ${hhExpenseMsaterInstance.journalLine}"
		
		try {
			if (projectMap.containsKey(hhExpenseMsaterInstance.projectId)) {
				projectInstance = projectMap.get(hhExpenseMsaterInstance.projectId)
				projectInstance.attach()
			}  else {
				projectInstance = projectService.findByCode(hhExpenseMsaterInstance.projectId)
			}
			
			if(projectInstance) {
				// Project found
				// Populate in Map
				projectMap.put(hhExpenseMsaterInstance.projectId, projectInstance)
				
				// Check if GL Account Number is in the db
				if(glAccountNumberList.contains(hhExpenseMsaterInstance.account)) {
					// Yes GL Account Number found
					
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
					def actualExpenseInstance = defaultProjectServiceDetail.actualExpense
					if(! actualExpenseInstance) {
						
						actualExpenseInstance = new ProjectActualExpense().save(flush: true, faileOnError: true)
						defaultProjectServiceDetail.actualExpense = actualExpenseInstance
						defaultProjectServiceDetail.save(flush: true, failOnError: true)
					}
					
					// Check if the expense record is already imported
					if(! isExpenseImportedAlready(hhExpenseMsaterInstance)) {
						
						// Find the relavent expense code
						def expenseObj = null
						if(glAccountExpenseMap.containsKey(hhExpenseMsaterInstance.account)) 
						{
							expenseObj = glAccountExpenseMap.get(hhExpenseMsaterInstance.account)
						} else {
							expenseObj = Expense.findByGlAccountNumber(hhExpenseMsaterInstance.account)
							glAccountExpenseMap.put(hhExpenseMsaterInstance.account, expenseObj)
						}
						
						// New Expense
						projectActualExpenseDetailInstance = new ProjectActualExpenseDetail(hhExpenseMsaterInstance, projectInstance, expenseObj, systemJobUser)
						actualExpenseInstance.addToActualExpenseDetails(projectActualExpenseDetailInstance)
						//actualExpenseInstance.projectActualExpenseTotal = actualExpenseInstance.projectActualExpenseTotal.add(projectActualExpenseDetailInstance.monetaryAmount)
						actualExpenseInstance.save(flush: true, failOnError: true)
						
						// Create Planned Expense for this actual transaction
						// Below mentioned method will check if this expense has already been planned 
						createPlannedExpenseForActualExpense(projectInstance.id, expenseObj, actualExpenseInstance, 1, systemJobUser)
						
						recordsInserted++
						
						logString = "Success: " + logString + ", actualExpenseDetailId: " + projectActualExpenseDetailInstance?.id
						
						ImportExpenseJobRecordDTO insertedExpenseRecord = new ImportExpenseJobRecordDTO(hhExpenseMsaterInstance, projectActualExpenseDetailInstance, logString)
						insertedRecordsList.add(insertedExpenseRecord)
						
						// Insert this record in ExpenseJobLineRecord for future reference 
						ImportExpenseJobLineRecord successLineRecord = new ImportExpenseJobLineRecord(hhExpenseMsaterInstance, currentJob, expenseObj.id, logString, ImportExpenseStatusType.SUCCESS, systemJobUser).save(flush: true)
						
						hhExpenseMsaterInstance.descrshort = Constants.HH_EXPENSEMASTERIMPORTSTATUS_IMPORTED
						hhExpenseMsaterInstance.save(flush: true)
					} else {
						logString = "Already Imported: " + logString + ", actualExpenseDetailId: " + projectActualExpenseDetailInstance?.id
					}  // Do nothing if already processed
				} else {
					logString = "Failure: " + logString + " :: No Expense Found :: GLAccountNumber: ${hhExpenseMsaterInstance.account}"
					recordsFailed++
					
					if(! expenseNotfound.contains(hhExpenseMsaterInstance.account)) {
						expenseNotfound.add(hhExpenseMsaterInstance.account)
					}
					
					ImportExpenseJobRecordDTO failedExpenseRecord = new ImportExpenseJobRecordDTO(hhExpenseMsaterInstance, null, logString)
					failedRecordsList.add(failedExpenseRecord)
					
					// Insert this record in ExpenseJobLineRecord for future reference
					ImportExpenseJobLineRecord failedLineRecord = new ImportExpenseJobLineRecord(hhExpenseMsaterInstance, currentJob, null, logString, ImportExpenseStatusType.EXPENSE_NOT_FOUND, systemJobUser).save(flush: true)
				}
			} else {
				logString = "Failure: " + logString + " :: No Project found :: ProjectCode: ${hhExpenseMsaterInstance.projectId}"
				recordsFailed++
				
				if(! projectsNotfound.contains(hhExpenseMsaterInstance.projectId)) {
					projectsNotfound.add(hhExpenseMsaterInstance.projectId)
					
					// Populate HHMissingProject
					def hhMissingProject = HHMissingProject.findBySourceProjectId(hhExpenseMsaterInstance.projectId)
					if(! hhMissingProject) {
						hhMissingProject = new HHMissingProject(hhExpenseMsaterInstance, logString, HHMissingProjectStatusType.NOT_FOUND_IN_HHPC, currentJob, systemJobUser).save(flush: true)
						log.info "No Project found :: ProjectCode: ${hhExpenseMsaterInstance.projectId} Inserted in MissingProjects: ${hhMissingProject?.id}"
					}
				}
				
				ImportExpenseJobRecordDTO failedExpenseRecord = new ImportExpenseJobRecordDTO(hhExpenseMsaterInstance, null, logString)
				failedRecordsList.add(failedExpenseRecord)
				
				// Insert this record in ExpenseJobLineRecord for future reference
				ImportExpenseJobLineRecord failedLineRecord = new ImportExpenseJobLineRecord(hhExpenseMsaterInstance, currentJob, null, logString, ImportExpenseStatusType.PROJECT_NOT_FOUND, systemJobUser).save(flush: true)
			}

			log.info "Rec#: ${recordsProcessed} :: " + logString
			
		} catch (Exception e) {
			
			logString = "Failure: " + logString + ", actualExpenseDetailId: " + projectActualExpenseDetailInstance?.id
			
			recordsFailed++
			
			ImportExpenseJobRecordDTO failedExpenseRecord = new ImportExpenseJobRecordDTO(hhExpenseMsaterInstance, projectActualExpenseDetailInstance, e.getCause())
			failedRecordsList.add(failedExpenseRecord)
			
			// Insert this record in ExpenseJobLineRecord for future reference
			ImportExpenseJobLineRecord failedLineRecord = new ImportExpenseJobLineRecord(hhExpenseMsaterInstance, currentJob, null, e.getCause(), ImportExpenseStatusType.FAILURE, systemJobUser).save(flush: true)
			
			log.error e
			log.info "Rec#: ${recordsProcessed} :: " + logString
			
			throw new RuntimeException(e)
		}
		
		return [recordsProcessed: recordsProcessed, recordsFailed: recordsFailed, recordsInserted: recordsInserted]
	}
	
	def isExpenseImportedAlready(hhExpenseMsaterInstance) {
		def isImported = false
		
		def projectActualExpenseDetail = ProjectActualExpenseDetail.findByHhProjectIdAndJournalIdAndJournalLine(hhExpenseMsaterInstance.projectId, hhExpenseMsaterInstance.journalId, hhExpenseMsaterInstance.journalLine) 
		if(projectActualExpenseDetail) {
			isImported = true
		}
		
		return isImported 
	}
	
	def getImportExpenseSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsFailed, insertedRecordsList,
			failedRecordsList, projectsNotfound, expenseNotfound) {

		ImportExpenseJobSummaryDTO summary = new ImportExpenseJobSummaryDTO(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsFailed, insertedRecordsList,
			failedRecordsList, projectsNotfound, expenseNotfound)
		
		return summary as JSON
	}
}