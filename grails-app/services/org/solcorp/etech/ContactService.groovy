package org.solcorp.etech

import grails.transaction.Transactional

@Transactional
class ContactService {

    def serviceMethod() {

    }
	
	/**
	 * Contact list
	 * 
	 */
	def getContactList(params) {
		
		log.info "getContactList @ ContactService Start"
			
		def contactCriteria = Contact.createCriteria()
	
		def contactInstanceList = contactCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			createAlias("customer", "customer")
			and {
				eq("customer.id", params.long("customer.id"))
			}
			order("${params.sort ?: 'name'}","${params.order ?: 'asc'}")
		}
		
		log.info "Contact List is " + contactInstanceList.size()
		log.info "getContactList @ contactService End"
	
		return [contactInstanceList: contactInstanceList, contactInstanceCount: contactInstanceList.totalCount, params: params]
	}
}
