package org.solcorp.etech

import grails.transaction.Transactional

@Transactional
class COEService {

    def getCOEList(params) {
		log.info "getCOEList @ COEService Start"
			
		def coeCriteria = COE.createCriteria()
		
		def COEInstanceList = coeCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			and {				 
				if (params?.code) {
					like("code","%" + params?.code + "%")
				}
				
				if (params?.description) {
					like("description","%" + params?.description + "%")
				}
			}
			
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def COEInstanceCount = COEInstanceList.totalCount
		
		return [COEInstanceList: COEInstanceList, COEInstanceCount: COEInstanceCount, params: params]
    }
	
	/**
	 *
	 * @param coeInstance
	 * @return
	 */
	def saveCOE(COEInstance) {
		
		if (!COEInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
	}
	
	/**
	 *
	 * @param COEInstance
	 * @return
	 */
	def updateCOE(COEInstance) {
		if (!COEInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
	}
	
}
