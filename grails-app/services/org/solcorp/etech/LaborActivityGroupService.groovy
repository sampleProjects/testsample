package org.solcorp.etech

import grails.transaction.Transactional
import org.solcorp.etech.LaborActivityGroup

@Transactional
class LaborActivityGroupService {

    def serviceMethod() {

    }
	
	/**
	 *This method is used for getting count and list of LaborActivityGroup Data from DB 
	 */
	def getLaborActivityGroupList(params) {
		log.info "getLaborActivityGroupList @ LaborActivityGroupService Start"
		
		def laborActivityGroupCriteria = LaborActivityGroup.createCriteria();
		def laborActivityGroupCountCriteria = LaborActivityGroup.createCriteria();
		
		def laborActivityGroupInstanceList = laborActivityGroupCriteria.list(max : params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
				createAlias("coe", "c")
			and{
				
				if (params?.code) {
					like("code","%" + params?.code + "%")
				}
				if (params?.description) {
					ilike("description","%" + params?.description + "%")
				}
				if (params?.coe) {
					eq("c.id", Long.valueOf(params?.coe))
				}
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		def laborActivityGroupInstanceCount = laborActivityGroupInstanceList.totalCount
		log.info "LaborActivityGroup List is "+laborActivityGroupInstanceList.size()
		log.info "getLaborActivityGroupList @ LaborActivityGroupService End"
		
		return [laborActivityGroupInstanceList : laborActivityGroupInstanceList, laborActivityGroupInstanceCount : laborActivityGroupInstanceCount, params : params]
	}
	
	def getCOEList() {
		def coeCriteria = COE.createCriteria()
		
		def COEInstanceList = coeCriteria.list() {			
			order("code","asc")
		}		 
		return COEInstanceList
	}
	
	def getLaborActGrpList() {		 
		def laborActGrpList = LaborActivityGroup.findAll()				
		return laborActGrpList
	}
}
