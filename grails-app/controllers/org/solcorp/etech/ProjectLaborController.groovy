package org.solcorp.etech

import grails.converters.JSON
import grails.transaction.Transactional;

import java.text.SimpleDateFormat

class ProjectLaborController {

	static allowedMethods = [save : "POST", update : "PUT"]
	
	def laborActivityCodeService
	def laborActivityGroupService
    def create() {
		
		def projectServiceDetailInstance = ProjectServiceDetail.read(params?.id?:params?.projectDetailInstanceID)
		
		def laborActCodeList = LaborActivityCode.findAllByLaborActivityGroup(projectServiceDetailInstance?.service?.laborActivityGroup)
		def laborActGrpCodeList = [] 
		
		/*println "**********************"
		println "laborActCodeList: " + laborActCodeList
		println "laborActCodeList.size: " + laborActCodeList.size()
		println "**********************"*/
		
		//println "mainCodeList: " + laborActCodeList*.id
		
		//Project Labor: Added activities in planning from actual labor Details.
		if(projectServiceDetailInstance?.actualLabor) {
			projectServiceDetailInstance?.actualLabor?.laborDetails?.each { it ->
				if(!laborActCodeList.contains(it?.laborActivityCode)) {
					if(it?.laborActivityCode?.id) {
						laborActGrpCodeList.addAll(it?.laborActivityCode?.code)
					}
				}
			}
		}
		/*println "**********************"
		println "laborActCodeLis1: " + laborActCodeList
		println "laborActCodeList.size: " + laborActCodeList.size()
		println "**********************"*/
		
		if(projectServiceDetailInstance?.plannedLabor) {			
			projectServiceDetailInstance?.plannedLabor?.laborDetails?.each { it ->
				if(!laborActCodeList.contains(it?.laborActivityCode)) {					
					if(it?.laborActivityCode?.id) {
						laborActCodeList.add(it?.laborActivityCode)
					}
				}
			}
		}
		 
		/*println "**********************"
		println "laborActCodeLis2: " + laborActCodeList
		println "laborActCodeList.size: " + laborActCodeList.size()
		println "**********************"*/
		
		def finalRowsresult = [:]
		
		for(int i=0; i < laborActCodeList?.size() ; i++){
			finalRowsresult["row_"+i] = getLaborDataMap(laborActCodeList[i]?.id)
		}
		
		/*def laborActCodeUnusedList = []
		
		laborActCodeUnusedList.addAll(laborActCodeList)
		
		for(int i=0; i < laborActCodeList?.size(); i++) {
			
			for(int j=0; j < projectServiceDetailInstance?.plannedLabor?.laborDetails?.size(); j++) {
				
				if(laborActCodeList[i]?.id == projectServiceDetailInstance?.plannedLabor?.laborDetails[j]?.laborActivityCode?.id) {
					laborActCodeUnusedList.remove(laborActCodeList[i]);
				} else {
					continue;
				}
			}
		}*/
		
		/*def finalRowsUnusedresult = [:]
		for(int i=0; i < laborActCodeUnusedList?.size(); i++) {
			finalRowsUnusedresult["row_"+i] = getLaborDataMap(laborActCodeUnusedList[i]?.id)
		}*/
		
		//render(view : "create", model: [laborActCodeList: laborActCodeList, projectServiceDetailInstance: projectServiceDetailInstance, finalRowsresult: finalRowsresult, laborActCodeUnusedList: laborActCodeUnusedList, finalRowsUnusedresult: finalRowsUnusedresult])
		//System.out.println("Labor Activity Code::::"+laborActCodeList.size())		
		render(view : "create", model: [coeList:laborActivityGroupService.getCOEList(), laborActGrpCodeList:laborActGrpCodeList, laborActCodeList: laborActCodeList, projectServiceDetailInstance: projectServiceDetailInstance, finalRowsresult: finalRowsresult])
	}
	
	@Transactional
	def save(ProjectLabor projectLaborInstance){
		//println "params: " + params
		
		try {
			def projectServiceDetailInstance = ProjectServiceDetail.get(params?.projectDetailInstanceID)
			//println "projectServiceDetailInstance "+projectServiceDetailInstance?.id
			if(params?.projectLaborInstanceID) {
				projectLaborInstance = ProjectLabor.get(params?.projectLaborInstanceID)
			}
			
			projectServiceDetailInstance?.plannedLabor = projectLaborInstance
			
			BigDecimal projectLaborTotal = 0.00;
			BigDecimal billAmountTotal = 0.00;
			BigDecimal overHeadTotal = 0.00;
			BigDecimal laborStandardCostTotal = 0.00;
			
			//println "Max Rows"+params?.int('maxRows')
			if(params?.removedRecordId) {				
				def removedRecordIdList = params?.removedRecordId?.split(",")				
				removedRecordIdList.each { it->					
					def projectLaborDetailInstance =ProjectLaborDetail.get(Long.parseLong(it))
					//projectLaborDetailInstance.delete flush:true, failOnError:true										
					projectServiceDetailInstance?.plannedLabor.removeFromLaborDetails(projectLaborDetailInstance)			
					
				}
			}
			for(int i=0; i <= params?.int('maxRows'); i++) {
				//println "params[laborHours_+i]: " + params["laborHours_"+i]
				//println "params[laborHours_+i].toString().trim(): " + params["laborHours_"+i].toString().trim()
				
				if(params["laborHours_"+i] != null && params["laborHours_"+i].toString().trim() != "") {
					//if((params["laborHours_"+i] != null && params["laborHours_"+i].toString().trim() != "" && params["laborHours_"+i].toString().trim().equals("0.00") == false) || (params["laborHours_"+i] != null && params["laborHours_"+i].toString().trim() != "" && params["laborHours_"+i].toString().trim().equals("0.00") && params["storedRowID_"+i].toString().trim().length() > 0)) {
					
					ProjectLaborDetail projectLaborDetailInstance = null;
					//println "Stored Row ID::::"+params["storedRowID_"+i].toString().trim()
					if(params["storedRowID_"+i] != null && params["storedRowID_"+i].toString().trim() != "") {
						//Update Labor Project Details
						projectLaborDetailInstance = ProjectLaborDetail.get(params["storedRowID_"+i].toString().trim())
						projectLaborDetailInstance.hours = new BigDecimal(params["laborHours_"+i]?.toString()?.trim()?:0.00)
						projectLaborDetailInstance.standardRate = new BigDecimal(params["laborStdRate_var_"+i]?.toString()?.trim()?:0.00)
						projectLaborDetailInstance.standardCost = new BigDecimal(params["laborStdCost_var_"+i]?.toString()?.trim()?:0.00)
						projectLaborDetailInstance.overHead = new BigDecimal(params["laborOverHead_var_"+i]?.toString()?.trim()?:0.00)
						projectLaborDetailInstance.overHeadCost = new BigDecimal(params["laborOverHeadCost_var_"+i]?.toString()?.trim()?:0.00)
						projectLaborDetailInstance.totalCost = new BigDecimal(params["laborTotalCost_var_"+i]?.toString()?.trim()?:0.00)
						projectLaborDetailInstance.billRate = new BigDecimal(params["laborBillRate_var_"+i]?.toString()?.trim()?:0.00)
						projectLaborDetailInstance.billAmountTotal = new BigDecimal(params["laborBillAmount_var_"+i]?.toString()?.trim()?:0.00)
						LaborActivityCode laborActivityCodeInstance = LaborActivityCode.get(params["laborActivityCode_var_"+i]?.toString()?.trim());
						projectLaborDetailInstance.laborActivityCode = laborActivityCodeInstance
						
					}else{
						//println "Inside Else"
						//Insert Labor Project Details Added
						projectLaborDetailInstance = new ProjectLaborDetail()
						projectLaborDetailInstance.hours = new BigDecimal(params["laborHours_"+i]?.toString()?.trim()?:0.00)
						
						//println "*************************"
						//println "Standard Rate ${i}: " + params["laborStdRate_var_"+i]
						//println "*************************"
						
						projectLaborDetailInstance.standardRate = new BigDecimal(params["laborStdRate_var_"+i].toString().trim()?:0.00)
						projectLaborDetailInstance.standardCost = new BigDecimal(params["laborStdCost_var_"+i].toString().trim()?:0.00)
						projectLaborDetailInstance.overHead = new BigDecimal(params["laborOverHead_var_"+i].toString().trim()?:0.00)
						projectLaborDetailInstance.overHeadCost = new BigDecimal(params["laborOverHeadCost_var_"+i].toString().trim()?:0.00)
						projectLaborDetailInstance.totalCost = new BigDecimal(params["laborTotalCost_var_"+i].toString().trim()?:0.00)
						projectLaborDetailInstance.billRate = new BigDecimal(params["laborBillRate_var_"+i].toString().trim()?:0.00)
						projectLaborDetailInstance.billAmountTotal = new BigDecimal(params["laborBillAmount_var_"+i].toString().trim()?:0.00)
						LaborActivityCode laborActivityCodeInstance = LaborActivityCode.get(params["laborActivityCode_var_"+i].toString().trim());
						projectLaborDetailInstance.laborActivityCode = laborActivityCodeInstance
						projectServiceDetailInstance?.plannedLabor.addToLaborDetails(projectLaborDetailInstance)
						
					}
					
					/*if(params["laborStdCost_var_"+i] != null && params["laborStdCost_var_"+i].toString().trim() != ""){
						laborStandardCostTotal = laborStandardCostTotal + new BigDecimal(params["laborStdCost_var_"+i]?.toString()?.trim()?:0.00)
					}
					if(params["laborTotalCost_var_"+i] != null && params["laborTotalCost_var_"+i].toString().trim() != ""){
						projectLaborTotal = projectLaborTotal + new BigDecimal(params["laborTotalCost_var_"+i]?.toString()?.trim()?:0.00)
					}
					if(params["laborBillAmount_var_"+i] != null && params["laborBillAmount_var_"+i].toString().trim() != ""){
						billAmountTotal = billAmountTotal + new BigDecimal(params["laborBillAmount_var_"+i]?.toString()?.trim()?:0.00)
					}
					if(params["laborOverHeadCost_var_"+i] != null && params["laborOverHeadCost_var_"+i].toString().trim() != ""){
						overHeadTotal = overHeadTotal + new BigDecimal(params["laborOverHeadCost_var_"+i]?.toString()?.trim()?:0.00)
					}*/
					
				}/* else {
					println "Flow should not be here::"
				}*/
			}
			
			/*projectServiceDetailInstance?.plannedLabor?.laborStandardCostTotal = laborStandardCostTotal
			projectServiceDetailInstance?.plannedLabor?.projectLaborTotal = projectLaborTotal
			projectServiceDetailInstance?.plannedLabor?.billAmountTotal = billAmountTotal
			projectServiceDetailInstance?.plannedLabor?.overHeadTotal = overHeadTotal*/
			
			
			
			if(params?.txtlaborActivityDate){
				String date_s = params?.txtlaborActivityDate
				SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy")
				Date date_j = dt.parse(date_s)
				projectServiceDetailInstance?.plannedLabor.laborActivityDate = date_j
			}else{
				SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy")
				String date_s = dt.format(new Date())
				Date date_j = dt.parse(date_s)
				projectServiceDetailInstance?.plannedLabor.laborActivityDate = date_j
			}
			
			if(projectServiceDetailInstance?.plannedLabor?.id > 0){
				flash.message = message(code : 'default.updated.success.message', default : "Record updated successfully")
			}else{
				flash.message =  message(code : 'default.added.success.message', default : "Record added successfully")
			}
			
			//println "projectServiceDetailInstance?.plannedLabor: " + projectServiceDetailInstance?.plannedLabor
			projectServiceDetailInstance?.plannedLabor.save(flush: true, failOnError: true)
			//println "After projectServiceDetailInstance?.plannedLabor: " + projectServiceDetailInstance?.plannedLabor
			
			redirect(action: "create", params: [id: params?.projectDetailInstanceID]) 
		}catch(Exception e) {
			e.printStackTrace()
			 flash.message = message(code : 'default.general.error.save.message', null)
			 redirect(action: "create", params: [id: params?.projectDetailInstanceID])
			 return
		 }
	}
	
	def getLaborData() {
		def result = [:]
		def laborActivityCodeInstance = LaborActivityCode.get(params?.laborActivityCode) 
		result["laborActivityGroupCode"] = laborActivityCodeInstance?.laborActivityGroup?.code
		result["laborActivityGroupDesc"] = laborActivityCodeInstance?.description
		result["standardRate"] = laborActivityCodeInstance?.standardRate
		result["overHeadPer"] = laborActivityCodeInstance?.overHead
		result["billRate"] = laborActivityCodeInstance?.billRate
		def finalResult =  result as JSON
		render finalResult
	}
	
	def getLaborDataMap(codeId) {
		def result = [:]
		def laborActivityCodeInstance = LaborActivityCode.get(codeId)
		result["laborActivityGroupCode"] = laborActivityCodeInstance?.laborActivityGroup?.code
		result["laborActivityGroupDesc"] = laborActivityCodeInstance?.description
		result["standardRate"] = laborActivityCodeInstance?.standardRate
		result["overHeadPer"] = laborActivityCodeInstance?.overHead
		result["billRate"] = laborActivityCodeInstance?.billRate
		return result
	}
	
	def getLaborActivityCodeDetailsList() {
		render(template: "laborActivityCodeList" , model: laborActivityCodeService.getLaborActivityCodeDetailsList(params), layout: "ajax")
	}
	
	def addLaborActivityCodeDetail() {
		def result = [:]
		def laborActivityCodeInstance = LaborActivityCode.get(params?.laborActivityCodeId)
		result["id"] = laborActivityCodeInstance?.id
		result["laborActivityCode"] = laborActivityCodeInstance?.code
		result["laborActivityDesc"] = laborActivityCodeInstance?.description
		result["standardRate"] = laborActivityCodeInstance?.standardRate
		result["overHeadPer"] = laborActivityCodeInstance?.overHead
		result["billRate"] = laborActivityCodeInstance?.billRate
		render result as JSON
	}
}
