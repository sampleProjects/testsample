package org.solcorp.etech

import grails.transaction.Transactional
import org.solcorp.etech.ProductCategory

@Transactional
class ProductCategoryService {

    def serviceMethod() {

    }
	
	def getProductCategoryList(params) {
		log.info "getProductCategoryList @ ProductCategoryService Start"
		
		def productCategoryCriteria = null
		
		if(params?.productClassType == "PROJECT") {
			productCategoryCriteria = ProjectProductCategory.createCriteria();
		} else if(params?.productClassType == "MISCELLANEOUS") {
			productCategoryCriteria = MiscellaneousCategory.createCriteria();
		} else if(params?.productClassType == "EXPENSES") {
			productCategoryCriteria = ExpenseCategory.createCriteria();
		} else if(params?.productClassType == "SERVICES") {
			 productCategoryCriteria = ServiceCategory.createCriteria();
		}
		
		
		def productCategoryInstanceList = productCategoryCriteria.list(max : params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			and{
				if (params?.category) {
					like("category","%" + params?.category + "%")
				}else if(params?.dialogProductCategory){
					like("category","%" + params?.dialogProductCategory + "%")
				}
				if (params?.description) {
					ilike("description","%" + params?.description + "%")
				}else if (params?.dialogProductCategoryDescription) {
					ilike("description","%" + params?.dialogProductCategoryDescription + "%")
				}
			}
			order("${params.sort ?: 'category'}","${params.order ?: 'asc'}")
		}
		
		def productCategoryInstanceCount = productCategoryInstanceList.totalCount
		log.info "Product Category List is "+productCategoryInstanceList.size()
		log.info "getProductCategoryList @ ProductCategoryService End"
		
		return [productCategoryInstanceList : productCategoryInstanceList, productCategoryInstanceCount : productCategoryInstanceCount, params : params]
	}
}
