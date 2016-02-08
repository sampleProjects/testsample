package org.solcorp.etech

import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals
import grails.converters.JSON;
import grails.gorm.DetachedCriteria

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap

import org.quartz.CronTrigger
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerKey
import org.quartz.impl.matchers.GroupMatcher

import pl.touk.excel.export.WebXlsxExporter;

class CustomQuartzController {
	def sessionFactory
	def userService
	def jobRegisterService
	def grailsApplication
	
	String laborTxnJobStDate = "";
	String expenseStartDate = "";
	
    static final Map<String, Trigger> triggers = [:]
	static final List manualTriggerJobKeys = ['ImportCustomersJob','ImportLaborTransactionsJob','ImportEmployeesJob','ImportProjectsJob', 'ImportMissingProjectsJob', 'ImportExpensesJob']
	
    Scheduler quartzScheduler

    def index = {
        redirect(action: "list")
    }

    def list = {
		try{
		def jobsList = []
		def listJobGroups = quartzScheduler.jobGroupNames
		
		def jobRegisterCriteriaResults = []
		jobRegisterService.getJobRegisterDetailByClassNameMaxID(jobRegisterCriteriaResults)
		
		listJobGroups?.each {jobGroup ->
			quartzScheduler.getJobKeys(jobGroupEquals(jobGroup))?.each {jobKey ->
				def jobName = jobKey.name
				List<Trigger> triggers = quartzScheduler.getTriggersOfJob(jobKey)
				if (triggers) {
					triggers.each {trigger ->
						if(manualTriggerJobKeys.contains(trigger.key.name.toString().trim())){
							def currentJob = createJob(jobGroup, jobName, jobsList, trigger.key.name)
							currentJob.trigger = trigger
							def state = quartzScheduler.getTriggerState(trigger.key)
							currentJob.triggerStatus = Trigger.TriggerState.find {
								it == state
							} ?: "UNKNOWN"
						
						}
					}
				} else {
					createJob(jobGroup, jobName, jobsList)
				}
			}
		}
			/*if(params?.lastJobName){
				flash.message =  message(code : 'etech.customQuartz.jobName', default : params?.lastJobName+" is "+params?.lastJobStatus)
			}*/
		
			
			if(request.xhr) {
				flash.clear()
				render (template:"listJobs", model: [jobs: jobsList, now: new Date(), scheduler: quartzScheduler,jobRegisterCriteriaResults: jobRegisterCriteriaResults])
			}else{
				render(view: 'list', model: [jobs: jobsList, now: new Date(), scheduler: quartzScheduler,jobRegisterCriteriaResults: jobRegisterCriteriaResults])
			}
			
			
		}
		catch(Throwable t){
			redirect(action: "list")
		}
	}

    private createJob(String jobGroup, String jobName, List jobsList, String triggerName = "") {
        def currentJob = [group: jobGroup, name: jobName]
        jobsList << currentJob
        return currentJob
    }

    def stop = {
        def triggerKeys = quartzScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(params.triggerGroup))
        def key = triggerKeys?.find {it.name == params.triggerName}
        if (key) {
            def trigger = quartzScheduler.getTrigger(key)
            if (trigger) {
                triggers[params.jobName] = trigger
                quartzScheduler.unscheduleJob(key)
            } else {
                flash.message = "No trigger could be found for $key"
            }
        } else {
            flash.message = "No trigger key could be found for $params.triggerGroup : $params.triggerName"
        }
        redirect(action: "list", params: [lastJobName: params?.jobName?.toString()?.trim()?.substring(params?.jobName?.toString()?.trim()?.lastIndexOf('.')+1), lastJobStatus: 'stopped'])
    }

    def start = {
        def trigger = triggers[params.jobName]
        if (trigger) {
            quartzScheduler.scheduleJob(trigger)
        } else {
            flash.message = "No trigger could be found for $params.jobName"
        }
        redirect(action: "list", params: [lastJobName: params?.jobName?.toString()?.trim()?.substring(params?.jobName?.toString()?.trim()?.lastIndexOf('.')+1), lastJobStatus: 'scheduled'])
    }

    def pause = {
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(params.jobGroup))
        def key = jobKeys?.find {it.name == params.jobName}
        if (key) {
            quartzScheduler.pauseJob(key)
        } else {
            flash.message = "No job key could be found for $params.jobGroup : $params.jobName"
        }
        redirect(action: "list", params: [lastJobName: params?.jobName?.toString()?.trim()?.substring(params?.jobName?.toString()?.trim()?.lastIndexOf('.')+1), lastJobStatus: 'paused'])
    }

    def resume = {
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(params.jobGroup))
        def key = jobKeys?.find {it.name == params.jobName}
        if (key) {
            quartzScheduler.resumeJob(key)
        } else {
            flash.message = "No job key could be found for $params.jobGroup : $params.jobName"
        }
        redirect(action: "list", params: [lastJobName: params?.jobName?.toString()?.trim()?.substring(params?.jobName?.toString()?.trim()?.lastIndexOf('.')+1), lastJobStatus: 'resumed'])
    }

    def runNow = {
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(params.jobGroup))
        def key = jobKeys?.find {it.name == params.jobName}
		if (key.getName().equals("org.solcorp.etech.ImportEmployeesJob")) {
			//quartzScheduler.triggerJob(key)
			org.solcorp.etech.ImportEmployeesJob.triggerNow(["loggedInUserId": userService.getLoggedInUser().id])
		} else if(key.getName().equals("org.solcorp.etech.ImportDepartmentsJob")){
			org.solcorp.etech.ImportDepartmentsJob.triggerNow(["loggedInUserId": userService.getLoggedInUser().id])
		} else if(key.getName().equals("org.solcorp.etech.ImportCustomersJob")){
			org.solcorp.etech.ImportCustomersJob.triggerNow(["loggedInUserId": userService.getLoggedInUser().id])
		} else if(key.getName().equals("org.solcorp.etech.ImportProjectsJob")){
			org.solcorp.etech.ImportProjectsJob.triggerNow(["loggedInUserId": userService.getLoggedInUser().id])
		} else if(key.getName().equals("org.solcorp.etech.ImportLaborTransactionsJob")){
			org.solcorp.etech.ImportLaborTransactionsJob.triggerNow(["loggedInUserId": userService.getLoggedInUser().id, "laborTxnJobStDate": params?.laborTxnJobStDate])
		} else if(key.getName().equals("org.solcorp.etech.ImportMissingProjectsJob")){
			org.solcorp.etech.ImportMissingProjectsJob.triggerNow(["loggedInUserId": userService.getLoggedInUser().id])
		} else if(key.getName().equals("org.solcorp.etech.ImportExpensesJob")){
			org.solcorp.etech.ImportExpensesJob.triggerNow(["loggedInUserId": userService.getLoggedInUser().id, "expenseStartDate": params?.expenseStartDate])
		} else {
			flash.message = "No job key could be found for $params.jobGroup : $params.jobName"
		}
        redirect(action: "list", params:[lastJobName: params?.jobName?.toString()?.trim()?.substring(params?.jobName?.toString()?.trim()?.lastIndexOf('.')+1), lastJobStatus: 'running'])
    }

    def startScheduler = {
        quartzScheduler.start()
		
		grailsApplication.config.shouldJobrun = Boolean.TRUE
		
		redirect(action: "list")
    }

    def stopScheduler = {
        quartzScheduler.standby()
		
		grailsApplication.config.shouldJobrun = Boolean.FALSE
		
		redirect(action: "list")
    }
	
	def getJobHistory = {
		try {
			flash.clear()
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = jobRegisterService.getJobHistoryByClassName(params)
			
			def className = params?.className?.toString()?.trim()?.substring(params?.className?.toString()?.trim()?.lastIndexOf('.')+1)
			
			def totalRecords
			def domainName
			if(className.equals("ImportCustomersJob")){
				totalRecords = Customer.count()
				domainName = "Customer"
			} else if(className.equals("ImportEmployeesJob")){
				totalRecords = Employee.count()
				domainName = "Employee"
			} else if(className.equals("ImportProjectsJob")){
				totalRecords = Project.count()
				domainName = "Project"
			} else if(className.equals("ImportExpensesJob")){
				//totalRecords = Expenses.count()
				//domainName = "Expenses"
			} else if(className.equals("ImportDepartmentsJob")){
				totalRecords = Department.count()
				domainName = "Department"
			}else if(className.equals("ImportMissingProjectsJob")){
				totalRecords = Project.count()
				domainName = "Project"
			}
			
			if(request.xhr) {
				render (template:"listRecords", model: [result: modelMap, jobName: params?.className?.toString()?.trim()?.substring(params?.className?.toString()?.trim()?.lastIndexOf('.')+1), totalRecords: totalRecords, domainName: domainName?:''])
			}else{
				render (view: "jobHistory", model: [result: modelMap, jobName: params?.className?.toString()?.trim()?.substring(params?.className?.toString()?.trim()?.lastIndexOf('.')+1), totalRecords: totalRecords, domainName: domainName?:''])
			}
			
		}catch(Exception e) {
			log.error "Exception "+e
		}
	}
	
	def getSummaryOnJobId = {
		def result = [:]
		def jobRegisterInstance = JobRegister.get(params?.jobId)
		result["summary"] = jobRegisterInstance?.jobRegisterSummary?.summary?:'N/A'
		def finalResult =  result as JSON
		render finalResult
	}
	
	def getStackTraceOnJobId = {
		def result = [:]
		def jobRegisterInstance = JobRegister.get(params?.jobId)
		result["stacktrace"] = jobRegisterInstance?.jobRegisterSummary?.stacktrace?:'N/A'
		def finalResult =  result as JSON
		render finalResult
	}
	
	def jobDetail= {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		
		if(params?.detailType?.equals('cumulative')){
			
			def importLaborTransactionJobLineRecordList = ImportLaborTransactionJobLineRecord.executeQuery("from ImportLaborTransactionJobLineRecord where id in (select max(id) from ImportLaborTransactionJobLineRecord group by sourceTransactionId) ORDER BY jobRegister DESC",[max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0]);
			
			def importLaborTransactionJobLineRecordListCount = ImportLaborTransactionJobLineRecord.executeQuery("select count (*) from ImportLaborTransactionJobLineRecord where id in (select max(id) from ImportLaborTransactionJobLineRecord group by sourceTransactionId)");
			
			def importLaborTransactionJobLineRecordCount = importLaborTransactionJobLineRecordListCount[0]
			
			render (view: "jobDetail", model: [jobName: params?.jobName, importLaborTransactionJobLineRecordList: importLaborTransactionJobLineRecordList, importLaborTransactionJobLineRecordCount: importLaborTransactionJobLineRecordCount, params: params])
			
		}  else {
			
			def jobRegisterInstance = JobRegister.findById(params?.id)
			if(! jobRegisterInstance) {
				notFound()
				return
			}
			
			def importLaborTransactionJobLineRecordCriteria = ImportLaborTransactionJobLineRecord.createCriteria()
			def importLaborTransactionJobLineRecordCountCriteria = ImportLaborTransactionJobLineRecord.createCriteria()
			def importLaborTransactionJobLineRecordList = importLaborTransactionJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
				and{
					if (params?.id) {
						eq("jobRegister", JobRegister.findById(params?.id))
					}
				}
				order("jobRegister","desc")
		   }
			
			def importLaborTransactionJobLineRecordCount = importLaborTransactionJobLineRecordList.totalCount
			render (view: "jobDetail", model: [jobRegisterInstance: jobRegisterInstance, importLaborTransactionJobLineRecordList: importLaborTransactionJobLineRecordList, importLaborTransactionJobLineRecordCount: importLaborTransactionJobLineRecordCount, params: params])
		}
	}
	
	def getJobSummaryList() {
		try{
			if(request.xhr) {
				
				if(params?.detailType?.equals('cumulative')){
					
					def query = "from ImportLaborTransactionJobLineRecord where id in (select max(id) from ImportLaborTransactionJobLineRecord group by sourceTransactionId) ";
				
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							query = query + "and status != 'SUCCESS' "
						}else {
							query = query + "and status = '${params?.status}' "
						}
					}
					
					if (params?.sourceTransactionId) {
						query = query + "and sourceTransactionId like '%${params?.sourceTransactionId}%' "
					} 
					if (params?.employeeId) {
						query = query + "and employeeId like '%${params?.employeeId}%' "
					} 
					if (params?.projectId) {
						query = query + "and projectId like '%${params?.projectId}%' "
					} 
					if (params?.projectFullName) {
						query = query + "and projectFullName like '%${params?.projectFullName}%' "
					} 
					if (params?.projectCustRevId) {
						query = query + "and projectCustRevId like '%${params?.projectCustRevId}%' "
					} 
					
					query = query + " ORDER BY jobRegister DESC"
					
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					
					def importLaborTransactionJobLineRecordList = ImportLaborTransactionJobLineRecord.executeQuery(query, [max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0])
					
					def queryCount = "select count(*) from ImportLaborTransactionJobLineRecord where id in (select max(id) from ImportLaborTransactionJobLineRecord group by sourceTransactionId) ";
				
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							queryCount = queryCount + "and status != 'SUCCESS' "
						}else {
							queryCount = queryCount + "and status = '${params?.status}' "
						}
					}
					
					if (params?.sourceTransactionId) {
						queryCount = queryCount + "and sourceTransactionId like '%${params?.sourceTransactionId}%' "
					}
					if (params?.employeeId) {
						queryCount = queryCount + "and employeeId like '%${params?.employeeId}%' "
					}
					if (params?.projectId) {
						queryCount = queryCount + "and projectId like '%${params?.projectId}%' "
					}
					if (params?.projectFullName) {
						queryCount = queryCount + "and projectFullName like '%${params?.projectFullName}%' "
					}
					if (params?.projectCustRevId) {
						queryCount = queryCount + "and projectCustRevId like '%${params?.projectCustRevId}%' "
					}
					
					def importLaborTransactionJobLineRecordListCount = ImportLaborTransactionJobLineRecord.executeQuery(queryCount)
					
					def importLaborTransactionJobLineRecordCount = importLaborTransactionJobLineRecordListCount[0]
					
					render(template: "jobDetailList" , model: [jobName: params?.jobName, importLaborTransactionJobLineRecordList: importLaborTransactionJobLineRecordList, importLaborTransactionJobLineRecordCount: importLaborTransactionJobLineRecordCount, params: params], layout: "ajax")
					
				} else {
				
					def jobRegisterInstance = JobRegister.findById(params?.id)
					if(! jobRegisterInstance) {
						notFound()
						return
					}
				
					def importLaborTransactionJobLineRecordCriteria = ImportLaborTransactionJobLineRecord.createCriteria()
					def importLaborTransactionJobLineRecordCountCriteria = ImportLaborTransactionJobLineRecord.createCriteria()
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					def importLaborTransactionJobLineRecordList = importLaborTransactionJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
						and {
							
							if (params?.id) {
								eq("jobRegister", JobRegister.findById(params?.id))
							}
							if (params?.sourceTransactionId) {
								ilike("sourceTransactionId","%" + params?.sourceTransactionId + "%")
							}
							if (params?.status) {
								if(ImportLaborTransactionStatusType.valueOf(params?.status) == ImportLaborTransactionStatusType.FAILURE) {
									ne("status",ImportLaborTransactionStatusType.SUCCESS)
								} else{
									eq("status", ImportLaborTransactionStatusType.valueOf(params?.status))
								}
							}
							if (params?.employeeId) {
								ilike("employeeId","%" + params?.employeeId + "%")
							}
							if (params?.projectId) {
								ilike("projectId","%" + params?.projectId + "%")
							}
							if (params?.projectFullName) {
								ilike("projectFullName","%" + params?.projectFullName + "%")
							}
							if (params?.projectCustRevId) {
								ilike("projectCustRevId","%" + params?.projectCustRevId + "%")
							}
						}
						order("jobRegister","desc")
					}
		
					def importLaborTransactionJobLineRecordCount = importLaborTransactionJobLineRecordList.totalCount
					
					render(template: "jobDetailList" , model: [jobRegisterInstance: jobRegisterInstance, importLaborTransactionJobLineRecordList: importLaborTransactionJobLineRecordList, importLaborTransactionJobLineRecordCount: importLaborTransactionJobLineRecordCount, params: params], layout: "ajax")
				}
			}
		}
		catch(Exception e){
			log.error "Exception "+ e
		}
    }
	
	def downloadExcelSheet = {
		
		WebXlsxExporter webXlsxExporter = new WebXlsxExporter()
	
		webXlsxExporter.with {
			
			for (ImportLaborTransactionStatusType importLaborTransactionStatusType : ImportLaborTransactionStatusType.values()) {
				
				if(importLaborTransactionStatusType.value.equals("Success")){
					 continue;
				}	
				
				sheet(importLaborTransactionStatusType.value).with {
					
					def headers = []
					def withProperties = []
					
					setResponseHeaders(response, "ImportLaborTransactionsJob.xls")
					
					def query = "from ImportLaborTransactionJobLineRecord where id in (select max(id) from ImportLaborTransactionJobLineRecord group by sourceTransactionId) ";
					
					if(importLaborTransactionStatusType.value.equals("Failure")){
						query = query + " and status != '${ImportLaborTransactionStatusType.SUCCESS}'"
					} else{
						query = query + " and status = '${importLaborTransactionStatusType.name()}'"
					}
					
					query = query + " ORDER BY jobRegister DESC"
					
					def importLaborTransactionJobLineRecordList = ImportLaborTransactionJobLineRecord.executeQuery(query);
					
					if(importLaborTransactionStatusType.value.equals("Project Not Found")){
						headers = ['Project Id', 'Project Name']
						withProperties = ['projectId', 'projectFullName']
						fillHeader(headers)
						add(importLaborTransactionJobLineRecordList.unique{it?.projectId}, withProperties)
					} else if(importLaborTransactionStatusType.value.equals("Employee Not Found") || importLaborTransactionStatusType.value.equals("Paydept Not Found") || importLaborTransactionStatusType.value.equals("Labor Department Not Found") || importLaborTransactionStatusType.value.equals("Labor Activity Not Found") || importLaborTransactionStatusType.value.equals("Undefined Labor Activity")){
						headers = ['Employee Id']
						withProperties = ['employeeId']
						fillHeader(headers)
						add(importLaborTransactionJobLineRecordList.unique{it?.employeeId}, withProperties)
					} else if(importLaborTransactionStatusType.value.equals("Failure")){
						headers = ['Source Transaction Id', 'Employee Id', 'Project Id', 'Project Name', 'Transaction Id', 'Status']
						withProperties = ['sourceTransactionId', 'employeeId', 'projectId', 'projectFullName', 'transactionId', 'status']
						fillHeader(headers)
						add(importLaborTransactionJobLineRecordList, withProperties)
					} else if(importLaborTransactionStatusType.value.equals("Invalid Standard Rate")){
						headers = ['Source Transaction Id', 'Message']
						withProperties = ['sourceTransactionId', 'message']
						fillHeader(headers)
						add(importLaborTransactionJobLineRecordList, withProperties)
					} else {
						headers = ['Source Transaction Id', 'Employee Id', 'Project Id', 'Project Name', 'Transaction Id']
						withProperties = ['sourceTransactionId', 'employeeId', 'projectId', 'projectFullName', 'transactionId']
						fillHeader(headers)
						add(importLaborTransactionJobLineRecordList, withProperties)
					}
						
				}
			}
			save(response.outputStream)
		}
	}
	
	
	def jobExpensesDetail = {
		
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		
		if(params?.detailType?.equals('cumulative')){
			
			def importExpenseJobLineRecordList = ImportExpenseJobLineRecord.executeQuery("from ImportExpenseJobLineRecord where id in (select max(id) from ImportExpenseJobLineRecord group by hhProjectId, journalId, journalLine) ORDER BY id DESC");
			
			def importExpenseJobLineRecordCount = importExpenseJobLineRecordList.size()
			
			render (view: "jobExpensesDetail", model: [jobName: params?.jobName, importExpenseJobLineRecordList: importExpenseJobLineRecordList, importExpenseJobLineRecordCount: importExpenseJobLineRecordCount, params: params])
			
		} else {
			
			def jobRegisterInstance = JobRegister.findById(params?.id)
			if(! jobRegisterInstance) {
				notFound()
				return
			}
			
			def importExpenseJobLineRecordCriteria = ImportExpenseJobLineRecord.createCriteria()
			def importExpenseJobLineRecordList = importExpenseJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
				and{
					if (params?.id) {
						eq("jobRegister", JobRegister.findById(params?.id))
					}
				}
				order("id","desc")
			}
			def importExpenseJobLineRecordCount = importExpenseJobLineRecordList.totalCount
			render (view: "jobExpensesDetail", model: [jobRegisterInstance: jobRegisterInstance, importExpenseJobLineRecordList: importExpenseJobLineRecordList, importExpenseJobLineRecordCount: importExpenseJobLineRecordCount, params: params])
		}	
	} 
	
	def getJobExpensesList = {
		try{
			if(request.xhr) {
				
				if(params?.detailType?.equals('cumulative')){
					
					def query = "from ImportExpenseJobLineRecord where id in (select max(id) from ImportExpenseJobLineRecord group by hhProjectId, journalId, journalLine)"
					
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							query = query + "and status != 'SUCCESS'"
						}else {
							query = query + "and status = '${params?.status}'"
						}
					}
					if(params?.hhProjectId){
						query = query + "and hhProjectId like '%${params?.hhProjectId}%' "
					}
					if(params?.expenseId){
						query = query + "and expenseId = '${params?.expenseId}'"
					}
					
					query = query + "ORDER BY id DESC"
					
					def queryCount = "select count(*) from ImportExpenseJobLineRecord where id in (select max(id) from ImportExpenseJobLineRecord group by hhProjectId, journalId, journalLine)"
					
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							queryCount = queryCount + "and status != 'SUCCESS'"
						}else {
							queryCount = queryCount + "and status = '${params?.status}'"
						}
					}
					if(params?.hhProjectId){
						queryCount = queryCount + "and hhProjectId like '%${params?.hhProjectId}%' "
					}
					if(params?.expenseId){
						queryCount = queryCount + "and expenseId = '${params?.expenseId}'"
					}
					
					def importExpenseJobLineRecordList = ImportExpenseJobLineRecord.executeQuery(query, [max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0]);
		
					def importExpenseJobLineRecordListCount = ImportExpenseJobLineRecord.executeQuery(queryCount)
		
					def importExpenseJobLineRecordCount = importExpenseJobLineRecordListCount[0]
		
					render (template: "jobExpensesDetailList", model: [jobName: params?.jobName, importExpenseJobLineRecordList: importExpenseJobLineRecordList, importExpenseJobLineRecordCount: importExpenseJobLineRecordCount, params: params], layout: "ajax")
					
				} else {
				
					def jobRegisterInstance = JobRegister.findById(params?.id)
					if(! jobRegisterInstance) {
						notFound()
						return
					}
					
					def importExpenseJobLineRecordCriteria = ImportExpenseJobLineRecord.createCriteria()
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					def importExpenseJobLineRecordList = importExpenseJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
						and {
							if (params?.id) {
								eq("jobRegister", JobRegister.findById(params?.id))
							}
							if (params?.hhProjectId) {
								ilike("hhProjectId","%" + params?.hhProjectId + "%")
							}
							if (params?.expenseId) {
								eq("expenseId", Long.valueOf(params?.expenseId))
							}
							if (params?.status) {
								if(ImportExpenseStatusType.valueOf(params?.status) == ImportExpenseStatusType.FAILURE) {
									ne("status",ImportExpenseStatusType.SUCCESS)
								} else{
									eq("status", ImportExpenseStatusType.valueOf(params?.status))
								}
							}
						}
						order("id","desc")
					}
		
					def importExpenseJobLineRecordCount = importExpenseJobLineRecordList.totalCount
					
					render (template: "jobExpensesDetailList", model: [jobRegisterInstance: jobRegisterInstance, importExpenseJobLineRecordList: importExpenseJobLineRecordList, importExpenseJobLineRecordCount: importExpenseJobLineRecordCount, params: params], layout: "ajax")
				}
			}
		}
		catch(Exception e){
			log.error "Exception "+ e
		}
	}
	
	
	def downloadJobExpensesExcelSheet = {
		
		WebXlsxExporter webXlsxExporter = new WebXlsxExporter()
	
		webXlsxExporter.with {
			
			for (ImportExpenseStatusType importExpenseStatusType : ImportExpenseStatusType.values()) {
				
				if(importExpenseStatusType.value.equals("Success")){
					continue;
				}
				
				sheet(importExpenseStatusType.value).with {
					
					def headers = []
					def withProperties = []
					
					setResponseHeaders(response, "ImportExpenseDetail.xls")
				
					def query = "from ImportExpenseJobLineRecord where id in (select max(id) from ImportExpenseJobLineRecord group by hhProjectId, journalId, journalLine)"
					
					if(importExpenseStatusType.value.equals("Failure")){
						query = query + " and status != '${ImportExpenseStatusType.SUCCESS}'"
					} else{
						query = query + " and status = '${importExpenseStatusType.name()}'"
					}
					
					query = query + "ORDER BY id DESC"
					
					def importExpenseJobLineRecordList = ImportExpenseJobLineRecord.executeQuery(query);
					
					if(importExpenseStatusType.value.equals("Project Not Found")){
						
						headers = ['Project Id']
						withProperties = ['hhProjectId']
						fillHeader(headers)
						add(importExpenseJobLineRecordList.unique{it?.hhProjectId}, withProperties)
						
					} else if(importExpenseStatusType.value.equals("Expense Not Found")){
						
						headers = ['Account']
						withProperties = ['account']
						fillHeader(headers)
						add(importExpenseJobLineRecordList.unique{it?.account}, withProperties)
						
					} else {
					
						headers = ['Project Id' ,'Expense Id' , 'Status' ,'Business Unit' , 'Journal Id' ,'Journal Date' ,'Unpost Seq' ,'Financial Year' ,'Accounting Period' ,'Source' ,'Journal Hdr Status' ,'Posted Date' ,'Description' ,'Currency Code' ,'Journal Create Dttm' ,'Journal Date Orig' ,'Journal Line' ,'Account' ,'Department Id' ,'Operating Unit' ,'Product' ,'Monetary Amount' ,'Foreign Currency' ,'Foreign Amount' ,'Voucher Id' ,'Invoice Id' ,'Vendor Id' ,'Name' ,'Dttm Stamp' ,'Descr Short' ,'Message']
						
						withProperties = ['hhProjectId' ,'expenseId' ,'status' ,'businessUnit' ,'journalId' ,'journalDate' ,'unpostSeq' ,'fiscalYear' ,'accountingPeriod' ,'source' ,'jrnlHdrStatus' ,'postedDate' ,'descr' ,'currencyCd' ,'jrnlCreateDttm' ,'journalDateOrig' ,'journalLine' ,'account' ,'deptid' ,'operatingUnit' ,'product' ,'monetaryAmount' ,'foreignCurrency' ,'foreignAmount' ,'voucherId' ,'invoiceId' ,'vendorId' ,'name1' ,'dttm_stamp' ,'descrshort' ,'message']
						
						fillHeader(headers)
						add(importExpenseJobLineRecordList, withProperties)
					}	
				}
			}
			save(response.outputStream)
		}
	}
	
	def jobProjectDetail= {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		
		if(params?.detailType?.equals('cumulative')){
			
			def importProjectJobLineRecordList = ImportProjectJobLineRecord.executeQuery("from ImportProjectJobLineRecord where id in (select max(id) from ImportProjectJobLineRecord group by hhProjectId) ORDER BY jobRegister DESC",[max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0]);
			
			def importProjectJobLineRecordListCount = ImportProjectJobLineRecord.executeQuery("select count (*) from ImportProjectJobLineRecord where id in (select max(id) from ImportProjectJobLineRecord group by hhProjectId)");
			
			def importProjectJobLineRecordCount = importProjectJobLineRecordListCount[0]
			
			render (view: "jobProjectDetail", model: [jobName: params?.jobName, importProjectJobLineRecordList: importProjectJobLineRecordList, importProjectJobLineRecordCount: importProjectJobLineRecordCount, params: params])
			
		}  else {
			
			def jobRegisterInstance = JobRegister.findById(params?.id)
			if(! jobRegisterInstance) {
				notFound()
				return
			}
			
			def importProjectJobLineRecordCriteria = ImportProjectJobLineRecord.createCriteria()
			def importProjectJobLineRecordList = importProjectJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
				and{
					if (params?.id) {
						eq("jobRegister", JobRegister.findById(params?.id))
					}
				}
				order("jobRegister","desc")
		   }
			
			def importProjectJobLineRecordCount = importProjectJobLineRecordList.totalCount
			render (view: "jobProjectDetail", model: [jobRegisterInstance: jobRegisterInstance, importProjectJobLineRecordList: importProjectJobLineRecordList, importProjectJobLineRecordCount: importProjectJobLineRecordCount, params: params])
		}
	}
	
	def getJobProjectList() {
		try{
			if(request.xhr) {
				
				if(params?.detailType?.equals('cumulative')){
					
					def query = "from ImportProjectJobLineRecord where id in (select max(id) from ImportProjectJobLineRecord group by hhProjectId) ";
				
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							query = query + "and status in ('CUSTOMER_NOT_FOUND', 'OTHER') "
						} else if(params?.status.equals('SUCCESS')){
							query = query + "and status in ('INSERT_SUCCESS', 'UPDATE_SUCCESS', 'UPDATE_NOT_REQUIRED_SUCCESS') "
						} else {
							query = query + "and status = '${params?.status}' "
						}
					}
					
					if (params?.hhProjectId) {
						query = query + "and hhProjectId like '%${params?.hhProjectId}%' "
					} 
					if (params?.hhCustId) {
						query = query + "and hhCustId like '%${params?.hhCustId}%' "
					} 
					if (params?.name1) {
						query = query + "and name1 like '%${params?.name1}%' "
					} 
					
					query = query + " ORDER BY jobRegister DESC"
					
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					
					def importProjectJobLineRecordList = ImportProjectJobLineRecord.executeQuery(query, [max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0])
					
					def queryCount = "select count(*) from ImportProjectJobLineRecord where id in (select max(id) from ImportProjectJobLineRecord group by hhProjectId) ";
				
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							queryCount = queryCount + "and status in ('CUSTOMER_NOT_FOUND', 'OTHER') "
						} else if(params?.status.equals('SUCCESS')){
							queryCount = queryCount + "and status in ('INSERT_SUCCESS', 'UPDATE_SUCCESS', 'UPDATE_NOT_REQUIRED_SUCCESS') "
						} else {
							queryCount = queryCount + "and status = '${params?.status}' "
						}
					}
					
					if (params?.hhProjectId) {
						queryCount = queryCount + "and hhProjectId like '%${params?.hhProjectId}%' "
					} 
					if (params?.hhCustId) {
						queryCount = queryCount + "and hhCustId like '%${params?.hhCustId}%' "
					} 
					if (params?.name1) {
						queryCount = queryCount + "and name1 like '%${params?.name1}%' "
					} 
					
					def importProjectJobLineRecordListCount = ImportProjectJobLineRecord.executeQuery(queryCount)
					
					def importProjectJobLineRecordCount = importProjectJobLineRecordListCount[0]
					
					render(template: "jobProjectDetailList" , model: [jobName: params?.jobName, importProjectJobLineRecordList: importProjectJobLineRecordList, importProjectJobLineRecordCount: importProjectJobLineRecordCount, params: params], layout: "ajax")
					
				} else {
				
					def jobRegisterInstance = JobRegister.findById(params?.id)
					if(! jobRegisterInstance) {
						notFound()
						return
					}
				
					def importProjectJobLineRecordCriteria = ImportProjectJobLineRecord.createCriteria()
					def importProjectJobLineRecordCountCriteria = ImportProjectJobLineRecord.createCriteria()
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					def importProjectJobLineRecordList = importProjectJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
						and {
							
							if (params?.id) {
								eq("jobRegister", JobRegister.findById(params?.id))
							}
							if (params?.status) {
								if(ImportProjectStatusType.valueOf(params?.status) == ImportProjectStatusType.FAILURE) {
									'in' ("status" , [ImportProjectStatusType.CUSTOMER_NOT_FOUND, ImportProjectStatusType.OTHER])
								} else if (ImportProjectStatusType.valueOf(params?.status) == ImportProjectStatusType.SUCCESS){
									'in' ("status" , [ImportProjectStatusType.INSERT_SUCCESS ,ImportProjectStatusType.UPDATE_SUCCESS, ImportProjectStatusType.UPDATE_NOT_REQUIRED_SUCCESS])
								} else{
									eq("status", ImportProjectStatusType.valueOf(params?.status))
								}
							}
							if (params?.hhProjectId) {
								ilike("hhProjectId","%" + params?.hhProjectId + "%")
							}
							if (params?.hhCustId) {
								ilike("hhCustId","%" + params?.hhCustId + "%")
							}
							if (params?.name1) {
								ilike("name1","%" + params?.name1 + "%")
							}
						}
						order("jobRegister","desc")
					}
		
					def importProjectJobLineRecordCount = importProjectJobLineRecordList.totalCount
					
					render(template: "jobProjectDetailList" , model: [jobRegisterInstance: jobRegisterInstance, importProjectJobLineRecordList: importProjectJobLineRecordList, importProjectJobLineRecordCount: importProjectJobLineRecordCount, params: params], layout: "ajax")
				}
			}
		}
		catch(Exception e){
			log.error "Exception "+ e
		}
    }
	
	def downloadJobProjectExcelSheet = {
		
		WebXlsxExporter webXlsxExporter = new WebXlsxExporter()
	
		webXlsxExporter.with {
			
			for (ImportProjectStatusType importProjectStatusType : ImportProjectStatusType.values()) {
				
				if(importProjectStatusType.value.equals("Success")){
					continue;
				}
				
				sheet(importProjectStatusType.value).with {
					
					def headers = []
					def withProperties = []
					
					setResponseHeaders(response, "ImportProjectDetail.xls")
				
					def query = "from ImportProjectJobLineRecord where id in (select max(id) from ImportProjectJobLineRecord group by hhProjectId) "
					
					if(importProjectStatusType.value.equals("Failure")){
						query = query + " and status in ('CUSTOMER_NOT_FOUND', 'OTHER') "
					} else{
						query = query + " and status = '${importProjectStatusType.name()}' "
					}
					
					query = query + " ORDER BY jobRegister DESC"
					
					def importProjectJobLineRecordList = ImportProjectJobLineRecord.executeQuery(query);
					
					if(importProjectStatusType.value.equals("Project Not Found")){
						
						headers = ['Project Id']
						withProperties = ['hhProjectId']
						fillHeader(headers)
						add(importProjectJobLineRecordList.unique{it?.hhProjectId}, withProperties)
						
					} else if(importProjectStatusType.value.equals("Customer Not Found")){
						
						headers = ['Customer ID']
						withProperties = ['hhCustId']
						fillHeader(headers)
						add(importProjectJobLineRecordList.unique{it?.hhCustId}, withProperties)
						
					} else {
					
						headers = ['HH Project Id' ,'Business Unit' , 'Status' , 'Description' ,'Customer Id' ,'HH Customer Id' ,'Project Name' ,'Effective Status' ,'Start Date' ,'End Date' ,'Sales Person' ,'Name 2' ,'Descr Short' ,'Dttm Stamp' ,'Project Id' ,'Message']
						
						withProperties = ['hhProjectId' ,'businessUnit' ,'status' ,'descr' ,'custId' ,'hhCustId' ,'name1' ,'effStatus' ,'startDt' ,'endDt' ,'salesPerson' ,'name2' ,'descrshort' ,'dttmStamp' ,'projectId' ,'message']
						
						fillHeader(headers)
						add(importProjectJobLineRecordList, withProperties)
					}	
				}
			}
			save(response.outputStream)
		}
	}
	
	def jobCustomerDetail= {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		
		if(params?.detailType?.equals('cumulative')){
			
			def importCustomerJobLineRecordList = ImportCustomerJobLineRecord.executeQuery("from ImportCustomerJobLineRecord where id in (select max(id) from ImportCustomerJobLineRecord group by hhCustId) ORDER BY jobRegister DESC",[max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0]);
			
			def importCustomerJobLineRecordListCount = ImportCustomerJobLineRecord.executeQuery("select count (*) from ImportCustomerJobLineRecord where id in (select max(id) from ImportCustomerJobLineRecord group by hhCustId)");
			
			def importCustomerJobLineRecordCount = importCustomerJobLineRecordListCount[0]
			
			render (view: "jobCustomerDetail", model: [jobName: params?.jobName, importCustomerJobLineRecordList: importCustomerJobLineRecordList, importCustomerJobLineRecordCount: importCustomerJobLineRecordCount, params: params])
			
		}  else {
			
			def jobRegisterInstance = JobRegister.findById(params?.id)
			if(! jobRegisterInstance) {
				notFound()
				return
			}
			
			def importCustomerJobLineRecordCriteria = ImportCustomerJobLineRecord.createCriteria()
			def importCustomerJobLineRecordList = importCustomerJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
				and{
					if (params?.id) {
						eq("jobRegister", JobRegister.findById(params?.id))
					}
				}
				order("jobRegister","desc")
		   }
			
			def importCustomerJobLineRecordCount = importCustomerJobLineRecordList.totalCount
			render (view: "jobCustomerDetail", model: [jobRegisterInstance: jobRegisterInstance, importCustomerJobLineRecordList: importCustomerJobLineRecordList, importCustomerJobLineRecordCount: importCustomerJobLineRecordCount, params: params])
		}
	}
	
	def getJobCustomerList() {
		try{
			if(request.xhr) {
				
				if(params?.detailType?.equals('cumulative')){
					
					def query = "from ImportCustomerJobLineRecord where id in (select max(id) from ImportCustomerJobLineRecord group by hhCustId) ";
				
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							query = query + "and status = 'OTHER' "
						} else if(params?.status.equals('SUCCESS')){
							query = query + "and status in ('UPDATE_NOT_REQUIRED_SUCCESS', 'INSERT_SUCCESS', 'UPDATE_SUCCESS') "
						} else {
							query = query + "and status = '${params?.status}' "
						}
					}
					
					if (params?.hhCustId) {
						query = query + "and hhCustId like '%${params?.hhCustId}%' "
					} 
					if (params?.name1) {
						query = query + "and name1 like '%${params?.name1}%' "
					} 
					
					query = query + " ORDER BY jobRegister DESC"
					
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					
					def importCustomerJobLineRecordList = ImportCustomerJobLineRecord.executeQuery(query, [max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0])
					
					def queryCount = "select count(*) from ImportCustomerJobLineRecord where id in (select max(id) from ImportCustomerJobLineRecord group by hhCustId) ";
				
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							queryCount = queryCount + "and status = 'OTHER' "
						} else if(params?.status.equals('SUCCESS')){
							queryCount = queryCount + "and status in ('UPDATE_NOT_REQUIRED_SUCCESS', 'INSERT_SUCCESS', 'UPDATE_SUCCESS') "
						} else {
							queryCount = queryCount + "and status = '${params?.status}' "
						}
					}
					
					if (params?.hhCustId) {
						queryCount = queryCount + "and hhCustId like '%${params?.hhCustId}%' "
					} 
					if (params?.name1) {
						queryCount = queryCount + "and name1 like '%${params?.name1}%' "
					} 
					
					def importCustomerJobLineRecordListCount = ImportCustomerJobLineRecord.executeQuery(queryCount)
					
					def importCustomerJobLineRecordCount = importCustomerJobLineRecordListCount[0]
					
					render(template: "jobCustomerDetailList" , model: [jobName: params?.jobName, importCustomerJobLineRecordList: importCustomerJobLineRecordList, importCustomerJobLineRecordCount: importCustomerJobLineRecordCount, params: params], layout: "ajax")
					
				} else {
				
					def jobRegisterInstance = JobRegister.findById(params?.id)
					if(! jobRegisterInstance) {
						notFound()
						return
					}
				
					def importCustomerJobLineRecordCriteria = ImportCustomerJobLineRecord.createCriteria()
					def importCustomerJobLineRecordCountCriteria = ImportCustomerJobLineRecord.createCriteria()
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					def importCustomerJobLineRecordList = importCustomerJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
						and {
							
							if (params?.id) {
								eq("jobRegister", JobRegister.findById(params?.id))
							}
							if (params?.status) {
								if(ImportCustomerStatusType.valueOf(params?.status) == ImportCustomerStatusType.FAILURE) {
									eq ("status" , ImportProjectStatusType.OTHER)
								}else if(ImportCustomerStatusType.valueOf(params?.status) == ImportCustomerStatusType.SUCCESS) {
									'in' ("status" , [ImportCustomerStatusType.UPDATE_NOT_REQUIRED_SUCCESS, ImportProjectStatusType.INSERT_SUCCESS, ImportCustomerStatusType.UPDATE_SUCCESS])
								} else{
									eq("status", ImportCustomerStatusType.valueOf(params?.status))
								}
							}
							
							if (params?.hhCustId) {
								ilike("hhCustId","%" + params?.hhCustId + "%")
							}
							if (params?.name1) {
								ilike("name1","%" + params?.name1 + "%")
							}
						}
						order("jobRegister","desc")
					}
		
					def importCustomerJobLineRecordCount = importCustomerJobLineRecordList.totalCount
					
					render(template: "jobCustomerDetailList" , model: [jobRegisterInstance: jobRegisterInstance, importCustomerJobLineRecordList: importCustomerJobLineRecordList, importCustomerJobLineRecordCount: importCustomerJobLineRecordCount, params: params], layout: "ajax")
				}
			}
		}
		catch(Exception e){
			log.error "Exception "+ e
		}
    }
	
	def downloadJobCustomerExcelSheet = {
		
		WebXlsxExporter webXlsxExporter = new WebXlsxExporter()
	
		webXlsxExporter.with {
			
			for (ImportCustomerStatusType importCustomerStatusType : ImportCustomerStatusType.values()) {
				
				if(importCustomerStatusType.value.equals("Success")){
					continue;
				} 
				
				sheet(importCustomerStatusType.value).with {
					
					def headers = []
					def withProperties = []
					
					setResponseHeaders(response, "ImportCustomerDetail.xls")
				
					def query = "from ImportCustomerJobLineRecord where id in (select max(id) from ImportCustomerJobLineRecord group by hhCustId) "
					
					if(importCustomerStatusType.value.equals("Failure")){
						query = query + " and status = 'OTHER' "
					} else if(importCustomerStatusType.value.equals("Success")){
						query = query + " and status in ('UPDATE_NOT_REQUIRED_SUCCESS', 'INSERT_SUCCESS' , 'UPDATE_SUCCESS') "
					} else{
						query = query + " and status = '${importCustomerStatusType.name()}' "
					}
					
					query = query + " ORDER BY jobRegister DESC"
					
					def importCustomerJobLineRecordList = ImportCustomerJobLineRecord.executeQuery(query);
					
					headers = ['HH Customer Id' ,'Name' , 'Status' , 'HH Master Customer Id' ,'Customer Status' ,'Date Added' ,'Last Maint Dttm' ,'Dttm Stamp' ,'Descr Short' ,'Customer Id' ,'Message']
					
					withProperties = ['hhCustId' ,'name1' , 'status' , 'hhMasterCustId' ,'custStatus' ,'dateAdded' ,'lastMaintDttm' ,'dttm_stamp' ,'descrshort','customerId' ,'message']
					
					fillHeader(headers)
					add(importCustomerJobLineRecordList, withProperties)
					
				}
			}
			save(response.outputStream)
		}
	}
	
	def jobEmployeeDetail= {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		
		if(params?.detailType?.equals('cumulative')){
			
			def importEmployeeJobLineRecordList = ImportEmployeeJobLineRecord.executeQuery("from ImportEmployeeJobLineRecord where id in (select max(id) from ImportEmployeeJobLineRecord group by hhEmployeeId) ORDER BY jobRegister DESC",[max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0]);
			
			def importEmployeeJobLineRecordListCount = ImportEmployeeJobLineRecord.executeQuery("select count (*) from ImportEmployeeJobLineRecord where id in (select max(id) from ImportEmployeeJobLineRecord group by hhEmployeeId)");
			
			def importEmployeeJobLineRecordCount = importEmployeeJobLineRecordListCount[0]
			
			render (view: "jobEmployeeDetail", model: [jobName: params?.jobName, importEmployeeJobLineRecordList: importEmployeeJobLineRecordList, importEmployeeJobLineRecordCount: importEmployeeJobLineRecordCount, params: params])
			
		}  else {
			
			def jobRegisterInstance = JobRegister.findById(params?.id)
			if(! jobRegisterInstance) {
				notFound()
				return
			}
			
			def importEmployeeJobLineRecordCriteria = ImportEmployeeJobLineRecord.createCriteria()
			def importEmployeeJobLineRecordList = importEmployeeJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
				and{
					if (params?.id) {
						eq("jobRegister", JobRegister.findById(params?.id))
					}
				}
				order("jobRegister","desc")
		   }
			
			def importEmployeeJobLineRecordCount = importEmployeeJobLineRecordList.totalCount
			render (view: "jobEmployeeDetail", model: [jobRegisterInstance: jobRegisterInstance, importEmployeeJobLineRecordList: importEmployeeJobLineRecordList, importEmployeeJobLineRecordCount: importEmployeeJobLineRecordCount, params: params])
		}
	}
	
	
	def getJobEmployeeList() {
		try{
			if(request.xhr) {
				
				if(params?.detailType?.equals('cumulative')){
					
					def query = "from ImportEmployeeJobLineRecord where id in (select max(id) from ImportEmployeeJobLineRecord group by hhEmployeeId) ";
				
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							query = query + "and status in ('PAY_DEPT_NOT_FOUND', 'OTHER') "
						}else if(params?.status.equals('SUCCESS')){
							query = query + "and status in ('INSERT_SUCCESS', 'UPDATE_SUCCESS') "
						}else {
							query = query + "and status = '${params?.status}' "
						}
					}
					
					if (params?.hhEmployeeId) {
						query = query + "and hhEmployeeId like '%${params?.hhEmployeeId}%' "
					} 
					if (params?.firstName) {
						query = query + "and firstName like '%${params?.firstName}%' "
					} 
					if (params?.lastName) {
						query = query + "and lastName like '%${params?.lastName}%' "
					} 
					
					query = query + " ORDER BY jobRegister DESC"
					
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					
					def importEmployeeJobLineRecordList = ImportEmployeeJobLineRecord.executeQuery(query, [max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0])
					
					def queryCount = "select count(*) from ImportEmployeeJobLineRecord where id in (select max(id) from ImportEmployeeJobLineRecord group by hhEmployeeId) ";
				
					if(params?.status){
						if(params?.status.equals('FAILURE')){
							queryCount = queryCount + "and status in ('PAY_DEPT_NOT_FOUND', 'OTHER') "
						}else if(params?.status.equals('SUCCESS')){
							queryCount = queryCount + "and status in ('INSERT_SUCCESS', 'UPDATE_SUCCESS') "
						}else {
							queryCount = queryCount + "and status = '${params?.status}' "
						}
					}
					
					if (params?.hhEmployeeId) {
						queryCount = queryCount + "and hhEmployeeId like '%${params?.hhEmployeeId}%' "
					} 
					if (params?.firstName) {
						queryCount = queryCount + "and firstName like '%${params?.firstName}%' "
					} 
					if (params?.lastName) {
						queryCount = queryCount + "and lastName like '%${params?.lastName}%' "
					} 
					
					def importEmployeeJobLineRecordListCount = ImportEmployeeJobLineRecord.executeQuery(queryCount)
					
					def importEmployeeJobLineRecordCount = importEmployeeJobLineRecordListCount[0]
					
					render(template: "jobEmployeeDetailList" , model: [jobName: params?.jobName, importEmployeeJobLineRecordList: importEmployeeJobLineRecordList, importEmployeeJobLineRecordCount: importEmployeeJobLineRecordCount, params: params], layout: "ajax")
					
				} else {
				
					def jobRegisterInstance = JobRegister.findById(params?.id)
					if(! jobRegisterInstance) {
						notFound()
						return
					}
				
					def importEmployeeJobLineRecordCriteria = ImportEmployeeJobLineRecord.createCriteria()
					def importEmployeeJobLineRecordCountCriteria = ImportEmployeeJobLineRecord.createCriteria()
					if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
					def importEmployeeJobLineRecordList = importEmployeeJobLineRecordCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
						and {
							
							if (params?.id) {
								eq("jobRegister", JobRegister.findById(params?.id))
							}
							if (params?.status) {
								if(ImportEmployeeStatusType.valueOf(params?.status) == ImportEmployeeStatusType.FAILURE) {
									'in' ("status" , [ImportEmployeeStatusType.PAY_DEPT_NOT_FOUND , ImportEmployeeStatusType.OTHER])
								} else if(ImportEmployeeStatusType.valueOf(params?.status) == ImportEmployeeStatusType.SUCCESS) {
									'in' ("status" , [ImportEmployeeStatusType.INSERT_SUCCESS , ImportEmployeeStatusType.UPDATE_SUCCESS])
								} else{
									eq("status", ImportEmployeeStatusType.valueOf(params?.status))
								}
							}
							if (params?.hhEmployeeId) {
								ilike("hhEmployeeId","%" + params?.hhEmployeeId + "%")
							}
							if (params?.firstName) {
								ilike("firstName","%" + params?.firstName + "%")
							}
							if (params?.lastName) {
								ilike("lastName","%" + params?.lastName + "%")
							}
						}
						order("jobRegister","desc")
					}
		
					def importEmployeeJobLineRecordCount = importEmployeeJobLineRecordList.totalCount
					
					render(template: "jobEmployeeDetailList" , model: [jobRegisterInstance: jobRegisterInstance, importEmployeeJobLineRecordList: importEmployeeJobLineRecordList, importEmployeeJobLineRecordCount: importEmployeeJobLineRecordCount, params: params], layout: "ajax")
				}
			}
		}
		catch(Exception e){
			log.error "Exception "+ e
		}
    }
	
	def downloadJobEmployeeExcelSheet = {
		
		WebXlsxExporter webXlsxExporter = new WebXlsxExporter()
	
		webXlsxExporter.with {
			
			for (ImportEmployeeStatusType importEmployeeStatusType : ImportEmployeeStatusType.values()) {
				
				if(importEmployeeStatusType.value.equals("Success")){
					continue;
				}
				
				sheet(importEmployeeStatusType.value).with {
					
					def headers = []
					def withProperties = []
					
					setResponseHeaders(response, "ImportEmployeeDetail.xls")
				
					def query = "from ImportEmployeeJobLineRecord where id in (select max(id) from ImportEmployeeJobLineRecord group by hhEmployeeId) "
					
					if(importEmployeeStatusType.value.equals("Failure")){
						query = query + " and status in ('PAY_DEPT_NOT_FOUND', 'OTHER') "
					}else if(importEmployeeStatusType.value.equals("Success")){
						query = query + " and status in ('INSERT_SUCCESS', 'UPDATE_SUCCESS') "
					} else{
						query = query + " and status = '${importEmployeeStatusType.name()}' "
					}
					
					query = query + " ORDER BY jobRegister DESC"
					
					def importEmployeeJobLineRecordList = ImportEmployeeJobLineRecord.executeQuery(query);
					
					headers = ['HH Employee Id' ,'First Name' , 'Last Name' , 'Status' ,'Department' ,'Last Hire Date' ,'Create Date' ,'Modify Date' ,'Import Status' ,'Import Status Date' ,'Employee Id' ,'Message']
					
					withProperties = ['hhEmployeeId' ,'firstName' ,'lastName' , 'status', 'department' ,'lastHireDate' ,'createDate' ,'modifyDate' ,'importStatus' ,'importStatusDate' ,'employeeId' , 'message']
					
					fillHeader(headers)
					add(importEmployeeJobLineRecordList, withProperties)
					
				}
			}
			save(response.outputStream)
		}
	}
	
	def viewLaborSummary =  {
		def totalLaborTransactionList = HHLaborTransaction.executeQuery("Select year(kron.transactionDate) as Year, month(kron.transactionDate) as Month, count(*) as RecordsAvailable FROM HHLaborTransaction kron WHERE kron.projectId != '00000' and kron.hours > 0 GROUP BY  year(kron.transactionDate), month(kron.transactionDate) order by year(kron.transactionDate) desc, month(kron.transactionDate) desc");
		def pendingLaborTransactionList = HHLaborTransaction.executeQuery("Select year(kron.transactionDate) as Year, month(kron.transactionDate) as Month, count(*) as RecordsAvailable FROM HHLaborTransaction kron WHERE kron.importStatus = 'Loaded' and kron.projectId != '00000' and kron.hours > 0 GROUP BY  year(kron.transactionDate), month(kron.transactionDate) order by year(kron.transactionDate) desc, month(kron.transactionDate) desc");
		def importedLaborTransactionList = ProjectActualLaborDetail.executeQuery("Select year(lab.transactionDate) as Year, month(lab.transactionDate) as Month, count(*) as RecordsImported FROM ProjectActualLaborDetail lab where lab.hhProjectId != '00000' and lab.hours != 0 GROUP BY  year(lab.transactionDate), month(lab.transactionDate) order by year(lab.transactionDate) desc, month(lab.transactionDate) desc");
		def pendingTotalMap = [:]
		pendingLaborTransactionList.each { total ->
			pendingTotalMap.put(total[0]+"_"+total[1], total[2])
		}
		def importedTotalMap = [:]
		importedLaborTransactionList.each { total ->
			importedTotalMap.put(total[0]+"_"+total[1], total[2])
		}
		def laborTrnsRecordList = []
		totalLaborTransactionList.each { total ->
			def jobSummaryDetailDTO = new JobSummaryDetailDTO()
			jobSummaryDetailDTO.year = total[0]
			jobSummaryDetailDTO.month = total[1]
			jobSummaryDetailDTO.totalLaborTransaction = total[2]
			
			jobSummaryDetailDTO.pendingLaborTransaction =  pendingTotalMap.get(total[0]+"_"+total[1]) ?: 0
			jobSummaryDetailDTO.importedLaborTransaction = importedTotalMap.get(total[0]+"_"+total[1]) ?: 0
			laborTrnsRecordList.add(jobSummaryDetailDTO)
		}
		
		render (view: "viewSummary", model: [laborTrnsRecordList: laborTrnsRecordList])
	}
	def viewExpenseSummary =  {
		def totalLaborTransactionList = HHExpenseMaster.executeQuery("Select year(kron.postedDate) as Year, month(kron.postedDate) as Month, count(*) as RecordsAvailable FROM HHExpenseMaster kron GROUP BY  year(kron.postedDate), month(kron.postedDate) order by year(kron.postedDate) desc, month(kron.postedDate) desc");
		def pendingLaborTransactionList = HHExpenseMaster.executeQuery("Select year(kron.postedDate) as Year, month(kron.postedDate) as Month, count(*) as RecordsAvailable FROM HHExpenseMaster kron WHERE kron.descrshort = 'Loaded' GROUP BY  year(kron.postedDate), month(kron.postedDate) order by year(kron.postedDate) desc, month(kron.postedDate) desc");
		def importedLaborTransactionList = ProjectActualExpenseDetail.executeQuery("Select year(lab.postedDate) as Year, month(lab.postedDate) as Month, count(*) as RecordsImported FROM ProjectActualExpenseDetail lab GROUP BY  year(lab.postedDate), month(lab.postedDate) order by year(lab.postedDate) desc, month(lab.postedDate) desc");
		def pendingTotalMap = [:]
		pendingLaborTransactionList.each { total ->
			pendingTotalMap.put(total[0]+"_"+total[1], total[2])
		}
		def importedTotalMap = [:]
		importedLaborTransactionList.each { total ->
			importedTotalMap.put(total[0]+"_"+total[1], total[2])
		}
		def laborTrnsRecordList = []
		totalLaborTransactionList.each { total ->
			def jobSummaryDetailDTO = new JobSummaryDetailDTO()
			jobSummaryDetailDTO.year = total[0]
			jobSummaryDetailDTO.month = total[1]
			jobSummaryDetailDTO.totalLaborTransaction = total[2]
			
			jobSummaryDetailDTO.pendingLaborTransaction =  pendingTotalMap.get(total[0]+"_"+total[1]) ?: 0
			jobSummaryDetailDTO.importedLaborTransaction = importedTotalMap.get(total[0]+"_"+total[1]) ?: 0
			laborTrnsRecordList.add(jobSummaryDetailDTO)
		}
		
		render (view: "viewSummary", model: [laborTrnsRecordList: laborTrnsRecordList])
	}
	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'jobRegisterInstance.label', default: 'Job'), params.id])
				redirect action: "list", method: "GET"
			}
		}
	}

}
