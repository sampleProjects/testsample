package org.solcorp.etech

import grails.util.GrailsUtil

import org.solcorp.etech.JobStatusType

class ImportProjectsJob  {
	def HHProjectMasterService
	def jobRegisterService 
	def userService
	
	static triggers = {
		cron name: 'ImportProjectsJob', cronExpression: "0 30 1 * * ?"	// 1:30 AM
		
		/*
		cronExpression:    "s m h D M W Y"	
							| | | | | | `- Year [optional]
							| | | | | `- Day of Week, 1-7 or SUN-SAT, ?
							| | | | `- Month, 1-12 or JAN-DEC
							| | | `- Day of Month, 1-31, ?
							| | `- Hour, 0-23
							| `- Minute, 0-59
							`- Second, 0-59*/
	}
	
	def group = "dataSyncGroup"
	def description = "Import HH Projects to HHPC"
	
	def concurrent = false
	
	def execute(context){
		
		def user
		def summary
		
		def loggedInUserId = context?.mergedJobDataMap?.get("loggedInUserId")
		if(loggedInUserId) {
			user = userService.getUserById(loggedInUserId as Long)
		} else {
			user = userService.getSystemJobUser()
		}
		
		JobRegister newJob = jobRegisterService.start("ImportProjectsJob", this.class.name, user, Constants.DISPLAY_NAME_PROJECTS_JOB)
		log.info(newJob.name + " started at: " + newJob.startDate)
		
		try {
			def importOnlyMissingProjects = false
			
			summary = HHProjectMasterService.importHHProjects(newJob.id, null, importOnlyMissingProjects)
			newJob = jobRegisterService.end(newJob.id, JobStatusType.SUCCESS, null, null, summary)
		} catch (Exception e) {
			log.error("Error while executing the job: " + newJob.name + "Started at: " + newJob.startDate)
			log.error("Exception: " + e.stackTrace)
		
			newJob = jobRegisterService.end(newJob.id, JobStatusType.FAIL, GrailsUtil.sanitize(e), e.stackTrace.toString(), summary)
		} finally {
			log.info(newJob.name + " completed at: " + newJob.endDate)
		}
	}
}