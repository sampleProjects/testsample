package org.solcorp.etech

import grails.transaction.Transactional
import org.springframework.transaction.annotation.Propagation

import java.math.RoundingMode

class ProjectActualLaborService {
	
	def setDefaultActivityToActualLabor(strStartTrxId, strBatchSize) {
		log.info "setDefaultActivityToActualLabor @ ProjectActualLaborService - START"
		
		try {
			def startTrxId = strStartTrxId.toLong()
			
			// Find Actual Labor
			def projectActualLaborDetailCriteria = ProjectActualLaborDetail.createCriteria()
			def projectActualLaborDetailIdList = projectActualLaborDetailCriteria.list(max: strBatchSize ? strBatchSize.toLong() : 10000, offset: 0) {
				
				projections { 
					property("id") 
				}
				
				and {
					ge("id", startTrxId)
				}
				
				order("id", "asc")
			}
			
			if(projectActualLaborDetailIdList) {
				log.info "Start Id: ${projectActualLaborDetailIdList.first()} :: End Id: ${projectActualLaborDetailIdList.last()} :: Actual Records To be Processed: " + projectActualLaborDetailIdList.size()
				
				def undefinedActivity = LaborActivityCode.findByCode(Constants.DEFAULT_LABOR_ACTIVITY_CODE)
				def systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
				
				projectActualLaborDetailIdList.eachWithIndex {it, idx ->
					adjustDefaultActivityToActualLaborRecord(it, idx+1, undefinedActivity, systemJobUser)
				}
			} else {
				log.info "No records found with the given params."
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
		
		log.info "setDefaultActivityToActualLabor @ ProjectActualLaborService - END"
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def adjustDefaultActivityToActualLaborRecord(projectActualLaborDetailId, recordNumber, undefinedActivity, systemJobUser) {
		// log.info "adjustDefaultActivityToActualLaborRecord @ HHLaborTransactionService - START"
		
		def projectActualLaborDetailCriteria = ProjectActualLaborDetail.createCriteria()
		def projectActualLaborDetailInstance = projectActualLaborDetailCriteria.get {
			createAlias("laborActivityCode", "laborActivityCode")
			createAlias("employee", "employee")
			and {
				eq("id", projectActualLaborDetailId)
			}
		}
		
		//def projectActualLaborDetailInstance = ProjectActualLaborDetail.findById(projectActualLaborDetailId)
		def logString = "TrxId: ${projectActualLaborDetailInstance.id}"
		
		try {
			// fetch current activity
			def currentActivity = projectActualLaborDetailInstance.laborActivityCode
			
			// fetch default activity for employee
			def currentEmployee = projectActualLaborDetailInstance.employee
			def defaultActivityCodeForEmployee = currentEmployee.laborActivityCode
			
			logString = logString + " :: Current ActId: ${currentActivity?.id} :: Current ActCode: ${currentActivity?.code}"
			
			// Assigned undefined activity in case defaultActivityCodeForEmployee = null
			if(defaultActivityCodeForEmployee == null) {
				currentEmployee.laborActivityCode = undefinedActivity
				currentEmployee.laborActivityGroup = undefinedActivity.laborActivityGroup
				currentEmployee.save(flush: true)
				
				logString = "Undefined Activity Set :: Employee: ${currentEmployee.id} :: " + logString
				
				// Undefined Activity is now default activity for this employee
				defaultActivityCodeForEmployee = currentEmployee.laborActivityCode
			}
			
			// Check if the ActivityCode from transaction is same as default activity code for employee
			if(defaultActivityCodeForEmployee.id != currentActivity.id) {
				// No
				// Update the projectActualLaborDetailInstance
				
				logString = logString + " :: New ActId: ${defaultActivityCodeForEmployee?.id} :: New ActCode: ${defaultActivityCodeForEmployee?.code}"
			
				// Set defaultActivityForEmployee as currentActivity for transaction
				projectActualLaborDetailInstance.laborActivityCode = defaultActivityCodeForEmployee
			}
			
			projectActualLaborDetailInstance.standardRate = projectActualLaborDetailInstance.laborActivityCode.standardRate ?: BigDecimal.ZERO
			projectActualLaborDetailInstance.standardCost = projectActualLaborDetailInstance.hours.multiply(projectActualLaborDetailInstance.standardRate).setScale(4, RoundingMode.UP)
			projectActualLaborDetailInstance.overHeadPercentage = projectActualLaborDetailInstance.laborActivityCode.overHead ?: BigDecimal.ZERO
			
			projectActualLaborDetailInstance.standardOverheadCost = projectActualLaborDetailInstance.overHeadPercentage.multiply(projectActualLaborDetailInstance.standardCost).setScale(4, RoundingMode.UP).divide(BigDecimal.valueOf(100), 4, RoundingMode.UP)
			projectActualLaborDetailInstance.actualOverheadCost = projectActualLaborDetailInstance.overHeadPercentage.multiply(projectActualLaborDetailInstance.actualCost).setScale(4, RoundingMode.UP).divide(BigDecimal.valueOf(100), 4, RoundingMode.UP)
			
			projectActualLaborDetailInstance.standardTotalCost = projectActualLaborDetailInstance.standardCost.add(projectActualLaborDetailInstance.standardOverheadCost)
			projectActualLaborDetailInstance.actualTotalCost = projectActualLaborDetailInstance.actualCost.add(projectActualLaborDetailInstance.actualOverheadCost)
			
			projectActualLaborDetailInstance.updatedBy = systemJobUser
			projectActualLaborDetailInstance.lastUpdated = new Date()
			projectActualLaborDetailInstance.save(flush: true)
			
			logString = "Success :: Transaction Updated :: " + logString
			
			
			log.info "Record#: ${recordNumber} :: " + logString
			
		} catch (Exception e) {
			logString = "Failure :: " + logString
			
			log.info logString
			// log.info "adjustDefaultActivityToActualLaborRecord @ HHLaborTransactionService - END"
			
			throw new RuntimeException(e)
		}
	}
}