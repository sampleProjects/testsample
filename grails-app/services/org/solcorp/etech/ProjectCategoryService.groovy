package org.solcorp.etech

import grails.transaction.Transactional

@Transactional
class ProjectCategoryService {

    def getProjectCategoryList(params) {
		log.info "getProjectCategoryList @ ProjectCategoryService Start"
			
		def projectCategoryCriteria = ProjectCategory.createCriteria()
		
		def projectCategoryInstanceList = projectCategoryCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			and {				 
				if (params?.category) {
					like("category","%" + params?.category + "%")
				}
				
				if (params?.description) {
					like("description","%" + params?.description + "%")
				}			
			}
			order("${params.sort ?: 'category'}","${params.order ?: 'asc'}")
		}
		
		def projectCategoryInstanceCount = projectCategoryInstanceList.totalCount
		
		return [projectCategoryInstanceList: projectCategoryInstanceList, projectCategoryInstanceCount: projectCategoryInstanceCount, params: params]
    }
}
