package org.solcorp.etech

import grails.transaction.Transactional
import org.solcorp.etech.Product

@Transactional
class ProductService {

    def serviceMethod() {

    }
	
	/**
	 *This method is used for getting count and list of LaborActivityGroup Data from DB 
	 */
	def getProductList(params) {
		log.info "getProductList @ ProductService Start"
		
		def productCriteria = null	
		
		if(params?.productClassType == "PROJECT") {
			productCriteria = ProjectProduct.createCriteria();
		} else if(params?.productClassType == "MISCELLANEOUS") {
			productCriteria = Miscellaneous.createCriteria();
		} else if(params?.productClassType == "EXPENSES") {
			productCriteria = Expense.createCriteria();
		} else if(params?.productClassType == "SERVICES") {
			 productCriteria = Service.createCriteria();
		}
		
		
		def productInstanceList = productCriteria.list(max : params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			and{
								
				if (params?.code) {
					like("code","%" + params?.code + "%")
				}
				if (params?.description) {
					ilike("description","%" + params?.description + "%")
				}
				if (params?.productClassType == "EXPENSES" && params?.glAccountNumber) {
					ilike("glAccountNumber","%" + params?.glAccountNumber + "%")
				}
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		
		
		/*def productInstanceList = productCriteria.list(max : params?.max?:10, offset : params?.offset?:0) {
			and{
				if (params?.code) {
					like("code","%" + params?.code + "%")
				}
				if (params?.description) {
					ilike("description","%" + params?.description + "%")
				}				
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}*/
		def productInstanceCount = productInstanceList.totalCount
		log.info "Product List is "+productInstanceList.size()
		log.info "getProductList @ ProductService End"
		
		return [productInstanceList : productInstanceList, productInstanceCount : productInstanceCount, params : params]
	}
	
	def getProjectProductList() {
			
		def productCriteria = ProjectProduct.createCriteria()
		
		def projectProductList = productCriteria.list() {
			order("code", "asc")			
		}		
		return projectProductList
	}
	
	def getProjectProductList(params) {	
		
		def productCriteria = ProjectProduct.createCriteria();
		
		def projectProductInstanceList = productCriteria.list() {
			and{
				
			}	
			order("code", "asc")
		}			
		return projectProductInstanceList
	}
	
	def getServiceListByProjectProduct(productId) {
				
		def serviceCriteria = Service.createCriteria();
		
		def serviceInstanceList = serviceCriteria.list() {
			and{
				eq("product.id", Long.valueOf(productId))				
			}
			order("code", "asc")
		}
		return serviceInstanceList
	}
}
