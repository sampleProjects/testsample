package org.solcorp.etech

class CommonFilters {
    def filters = {
        all(controller: '*', action: '*', uriExclude: "/auth/login") {
            before = {
				if (request.cookies.find { 'maxPaginationRecords' == it?.name}) {
					params.max = request.cookies.find { 'maxPaginationRecords' == it?.name}?.value
				}else{
					params.max = Constants.DEFAULT_PAGINATION_RECORDS
				}
				params.offset = params.offset ? params.offset as Integer : 0
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
