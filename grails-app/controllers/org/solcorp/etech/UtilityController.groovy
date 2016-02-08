package org.solcorp.etech

class UtilityController {
	def employeeService
	def HHLaborTransactionService
	def HHExpenseMasterService
	def projectActualLaborService
	
	def syncEmployeeActivityCodes() {
		employeeService.syncEmployeeAndActivityCodes()
		render "Success"
	}
	
	def syncPlannedLaborWithActualLabor() {
		HHLaborTransactionService.syncPlannedLaborWithActualLabor()
		render "Success"
	}
	
	def syncPlannedExpenseWithActualExpense() {
		HHExpenseMasterService.syncPlannedExpenseWithActualExpense()
		render "Success"
	}
	
	def setDefaultActivityToActualLabor() {
		projectActualLaborService.setDefaultActivityToActualLabor(params.startTrxId, params.batchSize)
		render "Success"
	}
}
