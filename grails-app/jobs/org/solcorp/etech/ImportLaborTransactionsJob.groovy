package org.solcorp.etech

import grails.util.GrailsUtil 

import java.text.DateFormat
import java.text.SimpleDateFormat

import org.solcorp.etech.JobStatusType

class ImportLaborTransactionsJob  {
	def HHLaborTransactionService
	def jobRegisterService 
	def grailsApplication
	def userService
	
	static triggers = {
		cron name: 'ImportLaborTransactionsJob', cronExpression: "0 30 2 * * ?"	// 2.30 AM
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
	def description = "Import Labor Transaction History to HHPC"
	
	def concurrent = false
	
	def execute(context){
		
		def user
		def summary
		
		def loggedInUserId = context?.mergedJobDataMap?.get("loggedInUserId")
		def laborTxnJobStDate = context?.mergedJobDataMap?.get("laborTxnJobStDate")
		if(loggedInUserId) {
			user = userService.getUserById(loggedInUserId as Long)
		} else {
			user = userService.getSystemJobUser()
		}
		
		Date dataRangeFromDate = formatDate(laborTxnJobStDate) 
		Date dataRangeToDate = new Date()
		
		JobRegister newJob = jobRegisterService.start("ImportLaborTransactionsJob", this.class.name, user, dataRangeFromDate, dataRangeToDate, Constants.DISPLAY_NAME_LABOR_JOB)
		log.info(newJob.name + " started at: " + newJob.startDate + " :: For DataRange From: " + dataRangeFromDate + " To: " + dataRangeToDate)
		
		try {
			summary = HHLaborTransactionService.importLaborTransactions(newJob.id, laborTxnJobStDate)
			newJob = jobRegisterService.end(newJob.id, JobStatusType.SUCCESS, null, null, summary)
		} catch (Exception e) {
			log.error("Error while executing the job: " + newJob.name + "Started at: " + newJob.startDate)
			log.error("Exception: " + e.stackTrace)
		
			newJob = jobRegisterService.end(newJob.id, JobStatusType.FAIL, e.getCause(), e.stackTrace.toString(), summary)
		} finally {
			log.info(newJob.name + " completed at: " + newJob.endDate)
		}
	}
	
	def formatDate(laborTxnJobStDate) {
		if(laborTxnJobStDate != null && laborTxnJobStDate != "" && laborTxnJobStDate?.toString()?.trim().length() > 0){
			String dateReceivedFromParams = laborTxnJobStDate?.toString()?.trim();
			DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = userDateFormat.parse(dateReceivedFromParams);
			laborTxnJobStDate = date;
		}else{
			def today = new Date()
			def yesterday = today - 7
			laborTxnJobStDate = yesterday;
		}
		return laborTxnJobStDate;
	}
}