package org.solcorp.etech

import grails.transaction.Transactional

@Transactional
class ProjectExpenseService {

    def getExpenseCodeList(params) {			
		def expenseCriteria = Expense.createCriteria()
		def expenseList = expenseCriteria.list(max : params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			and{
				if (params?.code) {
					ilike("code", "%" + params?.code +"%")
				}
				if (params?.description) {
					ilike("description", "%" + params?.description + "%")
				}
			}
		}
		def expenseListCount = expenseList.totalCount
	return [expenseList: expenseList, expenseListCount: expenseListCount, params: params]
	}
	
	def removeProjectExpenseDetail(params) {
		def projectExpenseInstance =new ProjectExpense(params)
		def index = params?.removeIndex
		
		if(index!="" && index!= null && index.toInteger() >=0 )
			index = index.toInteger()
	   
		List projectExpenseDtlTempList = []
				
		if(projectExpenseInstance?.expenseDetails) {
			
			int temp = 0
			
			projectExpenseInstance?.expenseDetails.each {
				it?.discard()
				if(temp !=  index) {
					projectExpenseDtlTempList.add(it)
				}
				temp ++
			}
		 }
		//projectDetailInstance?.plannedExpense = projectExpenseInstance
		//projectDetailInstance?.plannedExpense?.expenseDetails =projectExpenseDtlTempList
	 
		return projectExpenseDtlTempList
	}
}
