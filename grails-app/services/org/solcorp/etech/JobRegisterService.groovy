package org.solcorp.etech

import java.security.acl.Group;

import grails.transaction.Transactional

@Transactional
class JobRegisterService {

	def userService
	
	def start(name, className, user, dataRangeFromDate, dataRangeToDate, displayName) {
		
		JobRegister currentJob = new JobRegister()
		
		currentJob.name = name
		currentJob.className = className
		currentJob.startDate = new Date()
		currentJob.status = JobStatusType.RUNNING
		currentJob.initiatedBy = user
		
		currentJob.dataRangeFrom = dataRangeFromDate
		currentJob.dataRangeTo = dataRangeToDate
		currentJob.displayName = displayName
		
		
		currentJob.hasMessage = false
		currentJob.hasMessage = false
		currentJob.hasMessage = false
		currentJob.save(flush: true, failOnError: true)
		
		return currentJob
	}
	
    def start(name, className, user, displayName) {
		
		JobRegister currentJob = new JobRegister()
		
		currentJob.name = name
		currentJob.className = className
		currentJob.startDate = new Date()
		currentJob.status = JobStatusType.RUNNING
		currentJob.initiatedBy = user
		currentJob.displayName = displayName
		currentJob.hasMessage = false
		currentJob.hasMessage = false
		currentJob.hasMessage = false
		currentJob.save(flush: true, failOnError: true)
		
		return currentJob
    }
	
	def end(jobId, status, message, stacktrace, summary) {
		JobRegister currentJob = JobRegister.findById(jobId)
		currentJob.jobRegisterSummary = new JobRegisterSummary()
		
		currentJob.status = status
		currentJob.endDate = new Date()
		currentJob.jobRegisterSummary.message = message
		currentJob.jobRegisterSummary.summary = summary
		currentJob.jobRegisterSummary.stacktrace = stacktrace
		
		if(message != null && message.toString().length() > 0){
			currentJob.hasMessage = true
		}
		if(stacktrace != null && stacktrace.toString().length() > 0){
			currentJob.hasStacktrace = true
		}
		if(summary != null && summary.toString().length() > 0){
			currentJob.hasSummary = true
		}
		 
		currentJob.save(flush: true, failOnError: true)
		
		return currentJob
	}
	
	def getJobRegisterDetailByClassNameMaxID(jobRegisterCriteriaResults){
		
		def result1 = JobRegister.createCriteria().list{
			and{
				projections{
					max("id")
				}
				groupProperty("className")
			}
		}
		
		if(result1 != null && result1.size() > 0){
			for(int i=0;i<result1?.size();i++){
				def jobRegisterDetailCriteria = JobRegister.createCriteria();
				def result = jobRegisterDetailCriteria.list{
					and{
						eq("id", result1[i][0])
					}
				}
				
				if(result != null && result.size() > 0 && result[0] != null){
					jobRegisterCriteriaResults << result[0]
				}
			}
			//println "jobRegisterCriteriaResults"+jobRegisterCriteriaResults
		}
	}
	
	def getJobHistoryByClassName(params){
		def jobRegisterDetailCriteria = JobRegister.createCriteria();
		
		def jobHistoryList = jobRegisterDetailCriteria.list(max : params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0){
			and{
				eq("className", params?.className)
			}
			order("startDate","desc")
		}
		def jobListCount = jobHistoryList.totalCount
		return [jobHistoryList: jobHistoryList, jobListCount: jobListCount, params: params]
	}
	
	def getLastSuccessfulRunDate(jobName) {
		def jobRegisterDetailCriteria = JobRegister.createCriteria();
		
		def maxRunDate = jobRegisterDetailCriteria.get {
			projections {
				max "endDate"
			}
			
			and{
				eq("name", jobName)
				eq("status", JobStatusType.SUCCESS)
			}
		}
		
		println "maxRunDate: " + maxRunDate
		println "maxRunDate: " + maxRunDate.class
		
		return maxRunDate
	}
	
}
