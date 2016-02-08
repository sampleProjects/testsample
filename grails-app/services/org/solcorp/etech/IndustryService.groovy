package org.solcorp.etech

import grails.transaction.Transactional

@Transactional
class IndustryService {

	def getIndustryList(params) {
		log.info "getIndustryList @ IndustryService Start"
		def industryCriteria = Industry.createCriteria()
		def industryInstanceList = industryCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			and {				 
				if (params?.code) {
					like("code","%" + params?.code + "%")
				}
				if (params?.name) {
					like("name","%" + params?.name + "%")
				}
				if (params?.description) {
					like("description","%" + params?.description + "%")
				}
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		def industryInstanceCount = industryInstanceList.totalCount
		log.info "getIndustryList @ IndustryService End"
		return [industryInstanceList: industryInstanceList, industryInstanceCount: industryInstanceCount, params: params]
    }
	
	/**
	 *
	 * @param industryInstance
	 * @return
	 */
	def saveIndustry(industryInstance) {
		
		if (!industryInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
	}
	
	/**
	 *
	 * @param industryInstance
	 * @return
	 */
	def updateIndustry(industryInstance) {
		if (!industryInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
	}
}
