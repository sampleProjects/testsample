package org.solcorp.etech

import grails.transaction.Transactional

import org.hibernate.Criteria

@Transactional
class CustomerService {

    def serviceMethod() {

    }
	
	/**
	 * Customer list 
	 * 
	 */
	def getCustomerList(params) {
		
		log.info "getCustomerList @ CustomerService Start"
			
		def customerCriteria = Customer.createCriteria()
		
		def customerInstanceList = customerCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			
			if(params?.code) {
				ilike("code", '%' + params?.code + '%')
			}
			if(params?.name) {
				ilike("name", '%' + params?.name + '%')
			}
			if(params?.type) {
				ilike("type", '%' + params?.type + '%')
			}
			if(params?.category) {
				ilike("category", '%' + params?.category + '%')
			}
			if(params?.status) {
				eq("status", StatusType.valueOf(params?.status))
			}
			if(params?.priceLevel) {
				eq("priceLevel",  PriceLevelType.valueOf(params?.priceLevel))
			}
			if(params?.paymentTerms) {
				eq("paymentTerms", PaymentTermsType.valueOf(params?.paymentTerms))
			}
			if(params?.creditCode) {
				eq("creditCode", CreditCodeType.valueOf(params?.creditCode))
			}
			if(params?.creditLimit) {
				eq("creditLimit", new java.math.BigDecimal(params.creditLimit))
			}
			if(params?.statementType) {
				eq("statementType", StatementType.valueOf(params?.statementType))
			}
			if(params?.outsideRep) {
				eq("outsideRep",params.long('outsideRep'))
			}
			if(params?.federalId) {
				ilike("federalId", '%' + params?.federalId + '%')
			}
			if(params?.stateId) {
				ilike("stateId", '%' + params?.stateId + '%')
			}
			if(params?.comments) {
				ilike("comments", '%' + params?.comments + '%')
			}
			if (params?.acctExecutiveTxt) {
				createAlias('acctExecutive', 'employeeAcctExecutiveTxt')
				ilike("employeeAcctExecutiveTxt.code","%" + params?.acctExecutiveTxt + "%")
			}
			if (params?.salesExecutiveTxt) {
				createAlias('salesExecutive', 'employeeSalesExecutiveTxt')
				ilike("employeeSalesExecutiveTxt.code","%" + params?.salesExecutiveTxt + "%")
			}
			if (params?.industryTxt) {
				createAlias('industry', 'industryTxt')
				ilike("industryTxt.code","%" + params?.industryTxt + "%")
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
	
		log.info "customer List is " + customerInstanceList.size()
		log.info "getCustomerList @ CustomerService End"
		
		return [customerInstanceList: customerInstanceList, customerInstanceCount: customerInstanceList.totalCount, params: params]
	}
	
	def getHHCustomerMstList(params) {
			
		def customerList = Customer.findAll().code
			
		def hhCustomerCriteria = HHCustomerMaster.createCriteria()
		def customerMstList = hhCustomerCriteria.list(max : params?.max, offset : params?.offset?:0) {
			and{
		
				if (params?.customerId) {
					ilike("hhCustId", "%" + params?.customerId +"%")
				}
				
				if (params?.name) {
					ilike("name1", "%" + params?.name + "%")
				}
					
				/*if(params?.isAssignedCustShow == 'false') {
					 if(customerList.size() > 0) {
						not { 'in'("hhCustId", customerList) }
					}
				}*/
			}
			order("hhCustId", "asc")
		}
			
		def customerMstListCount = customerMstList.totalCount
			 
		return [customerMstList: customerMstList, customerMstListCount: customerMstListCount, customerList: customerList, params: params]
	}
	
	def getSalesExecutiveList(params) {
		log.info "getSalesExecutiveList @ getSalesExecutiveList Start"
		
		def salesExecutiveCriteria = Employee.createCriteria()
		def salesExecutiveInstanceList = salesExecutiveCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			and {
		
				if (params?.code) {
					ilike("code", "%" + params?.code +"%")
				}
				
				if (params?.firstName) {
					ilike("firstName", "%" + params?.firstName + "%")
				}
				
				if (params?.lastName) {
					ilike("lastName", "%" + params?.lastName + "%")
				}
			
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def salesExecutiveInstanceCount = salesExecutiveInstanceList.totalCount
		
		log.info "getSalesExecutiveList @ getSalesExecutiveList End " 
		
		return [salesExecutiveInstanceList: salesExecutiveInstanceList, salesExecutiveInstanceCount: salesExecutiveInstanceCount, params: params]
	}
	
	def getIndustryList(params) {
		log.info "getIndustryList @ getIndustryList Start"
		
		def industryCriteria = Industry.createCriteria()
		def industryInstanceList = industryCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			and {
		
				if (params?.code) {
					ilike("code", "%" + params?.code +"%")
				}
				
				if (params?.name) {
					ilike("name", "%" + params?.name + "%")
				}
	
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def industryInstanceCount = industryInstanceList.totalCount
		
		log.info "getIndustryList @ getIndustryList End " 
		
		return [industryInstanceList: industryInstanceList, industryInstanceCount: industryInstanceCount, params: params]
	}
	
	
}
