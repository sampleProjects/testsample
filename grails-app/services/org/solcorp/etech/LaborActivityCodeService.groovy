package org.solcorp.etech

import grails.transaction.Transactional
import org.solcorp.etech.LaborActivityGroup
import org.solcorp.etech.LaborActivityCode

@Transactional
class LaborActivityCodeService {

    def serviceMethod() {

    }
	
	/**
	 *This method is used for getting count and list of LaborActivityCode Data from DB 
	 */
	def getLaborActivityCodeList(params) {
		log.info "getLaborActivityCodeList @ LaborActivityCodeService Start"
		def laborActivityCodeCriteria = LaborActivityCode.createCriteria();
		def laborActivityCodeCountCriteria = LaborActivityCode.createCriteria();		
		 
		def laborActivityCodeInstanceList = laborActivityCodeCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			createAlias('laborActivityGroup', 'l')
			and{
				if (params?.code) {
					ilike("code","%" + params?.code + "%")
				}
				if (params?.description) {
					ilike("description","%" + params?.description + "%")
				}
				//def laborActivityGroupParamName = "laborActivityGroup.id"
				//if(params[laborActivityGroupParamName] != null && params[laborActivityGroupParamName].toString().trim() != ""){
				if(params.long("laborActivityGroup.id") != null && params.long("laborActivityGroup.id").toString().trim() != ""){
					eq("l.id",params.long("laborActivityGroup.id"))
				}
				if (params?.standardRate) {
					eq("standardRate",new java.math.BigDecimal(params?.standardRate))
				}
				if (params?.operations){
					eq("operations",params?.operations)
				}
				if (params?.overHead) {
					eq("overHead",new java.math.BigDecimal(params?.overHead))
				}
								
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		def laborActivityCodeInstanceCount = laborActivityCodeInstanceList.totalCount
		log.info "LaborActivityCode List is "+laborActivityCodeInstanceList.size()
		log.info "getLaborActivityCodeList @ LaborActivityCodeService End"
		
		return [laborActivityCodeInstanceList : laborActivityCodeInstanceList, laborActivityCodeInstanceCount : laborActivityCodeInstanceCount, params : params]
	}
	
	def getLaborActCodeList() {
		
		def laborActCodeList = LaborActivityCode.findAll()
				
		return laborActCodeList
	}
	
	def getAllLaborActivityDepartment() {
		def laborActivityGroupCriteria = LaborActivityGroup.createCriteria()
		def laborActivityGroupList = laborActivityGroupCriteria.list() {
			order("code","asc")
		}
		return laborActivityGroupList
	}
	
	def getLaborActivityCodeDetailsList(params) {
		def laborActivityCodeCriteria = LaborActivityCode.createCriteria()
		def laborActivityCodeList = laborActivityCodeCriteria.list(max : params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			createAlias("laborActivityGroup", "group")
			createAlias("laborActivityGroup.coe", "coe")
			
			and{
				if (params?.coe) {
					ilike("coe.code", "%" + params?.coe +"%")
				}
				if (params?.code) {
					ilike("code", "%" + params?.code +"%")
				}
			}
			
			order("coe.code", "asc")
			order("group.code", "asc")
		}
		def laborActivityCodeListCount = laborActivityCodeList.totalCount
	return [laborActivityCodeList: laborActivityCodeList, laborActivityCodeListCount: laborActivityCodeListCount, params: params]
	}
}
