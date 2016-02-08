package org.solcorp.etech

import grails.converters.JSON
import grails.transaction.Transactional;
import java.text.SimpleDateFormat

class ProjectExpenseController {

    static allowedMethods = [save : "POST", update : "PUT" ,removeProjectExpenseDtl :"POST"]
	
	def projectExpenseService
	
    def create() {
		def projectServiceDetailInstance = ProjectServiceDetail.read(params?.id?:params?.projectDetailInstanceID)		
		def expenseDetailsList = projectServiceDetailInstance?.plannedExpense?.expenseDetails			
		render(view : "create", model: [expenseDetailsList: expenseDetailsList,projectServiceDetailInstance: projectServiceDetailInstance])
	}
		
	def save(){
		try{
			def projectServiceDetailInstance = ProjectServiceDetail.get(params?.projectDetailInstanceID)
			
			def projectExpenseInstance = null
						
			if(projectServiceDetailInstance?.plannedExpense) {
				projectExpenseInstance = projectServiceDetailInstance?.plannedExpense
				projectExpenseInstance?.expenseDetails?.clear()
				projectExpenseInstance.properties = params				
			}else {
				projectExpenseInstance = new ProjectExpense(params)
			}

			
			//projectExpenseInstance.id = params?.id //it will give error of unsaved transient object
			
			//projectExpenseInstance.projectExpenseTotal = new BigDecimal(0.00)
			
			projectServiceDetailInstance?.plannedExpense = projectExpenseInstance
			
			/*if(projectServiceDetailInstance?.plannedExpense?.expenseDetails) {
				for(int i=0; i < projectServiceDetailInstance?.plannedExpense?.expenseDetails.size(); i++) {
					projectExpenseInstance.projectExpenseTotal = projectExpenseInstance.projectExpenseTotal + projectServiceDetailInstance?.plannedExpense?.expenseDetails[i]?.totalCost
				}
			}*/
			
			projectExpenseInstance.validate()
			
			if (projectExpenseInstance.hasErrors()) {
				def expenseDetailsList = projectServiceDetailInstance?.plannedExpense?.expenseDetails
				respond projectExpenseInstance.errors, view: "create", model: [expenseDetailsList: expenseDetailsList, projectServiceDetailInstance: projectServiceDetailInstance]
				return
			}else{
				if(projectServiceDetailInstance?.plannedExpense){
					flash.message = message(code : 'default.updated.success.message', default : "Record updated successfully")
				}else{
					flash.message =  message(code : 'default.added.success.message', default : "Record added successfully")
				}
				projectServiceDetailInstance?.plannedExpense.save(flush: true, failOnError: true)
			}
			redirect(action: "create", params: [id: params?.projectDetailInstanceID])
		}catch(Exception e) {
			 flash.message = message(code : 'default.general.error.save.message', null)
			 redirect(action: "create", params: [id: params?.projectDetailInstanceID])
			 return
		 }
		
	}
	
	def getExpenseData() {
		def result = [:]
		def expenseInstance = Expense.get(params?.expenseId)
		result["unitCost"] = expenseInstance?.standardRate?:0.00
		
		def finalResult =  result as JSON
		render finalResult
	}
	
	def removeProjectExpenseDtl() {
		render (template : "projectExpenseDetailList", model : ["expenseDetailsList": projectExpenseService.removeProjectExpenseDetail(params)])
	}
	
	def addExpenseDetail() {
		def expenseInstance = Expense.get(params?.expId)		
		render (template : "projectExpenseDetail", model:[i : params.i, expenseInstance: expenseInstance, isFromActualExpense: params?.isFromActualExpense])
	}
	
	def getExpenseList() {
		render(template: "expenseCodeList" , model: projectExpenseService.getExpenseCodeList(params), layout: "ajax")
	}
	
}
