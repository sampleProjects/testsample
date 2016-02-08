package org.solcorp.etech

import grails.transaction.Transactional

/**
 * @author rrsavani
 *
 */
@Transactional()
class AddressService {

    def serviceMethod() {

    }
	
	/**
	 *
	 * Address list 
	 */
	def getAddressList(params) {
		
		log.info "getAddressList @ AddressService Start"
			
		def addressCriteria = Address.createCriteria()
		
		def addressInstanceList = addressCriteria.list(max: params?.max?:10, offset: params?.offset?:0) {
			createAlias("customer", "customer")
			and {
				eq("addressType", AddressType.SHIPPING)
				eq("customer.id", params.long('customer.id'))
			}
			order("${params.sort ?: 'line1'}","${params.order ?: 'asc'}")
		}
		
		log.info "Address List is " + addressInstanceList.size()
		log.info "getAddressList @ addressService End"
		return [addressInstanceList: addressInstanceList, addressInstanceCount: addressInstanceList.totalCount, params: params]
	}
}
