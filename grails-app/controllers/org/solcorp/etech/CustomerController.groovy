package org.solcorp.etech

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional(readOnly = true)
class CustomerController {

	static allowedMethods = [save: "POST", update: "PUT"]
	
	def customerService
	
	def userService
	/**
	 * This action used to refresh create form
	 * 
	 */
	def clear() {
		redirect action: "create", method: "GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to  Create form Reset
	 */
	def newRecord() {
		flash.message =  message(code : 'etech.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		redirect action: "create", method: "GET", params: [previousAction: actionName]
	}
	
	def create() {
	
		def customer = new Customer()
		def address = new Address ()
		def contact = new Contact()
		
		if(params?.previousAction.equals("newRecord")) {
			customer.creditLimit = 0.00
		}
		
		render(view: 'create', model: [customerInstance: customer, addressInstance: address, contactInstance: contact])
	}

	@Transactional
	def save(Customer customerInstance, Address addressInstance) {
		try {

			if (customerInstance == null) {
				notFound()
				return
			}
			
			if(params?.acctExecutiveTxt){
				def employee = Employee.findByCode(params?.acctExecutiveTxt)
				if(employee){
					customerInstance?.acctExecutive = employee
					customerInstance.validate()
				}else{
					customerInstance?.acctExecutiveTxt = params?.acctExecutiveTxt
					customerInstance.errors.rejectValue('acctExecutiveTxt', 'org.solcorp.etech.Customer.acctExecutiveTxt.error.notFound')
				}
			}
			
			if(params?.salesExecutiveTxt){
				def employee = Employee.findByCode(params?.salesExecutiveTxt)
				if(employee){
					customerInstance?.salesExecutive = employee
					customerInstance.validate()
				}else{
					customerInstance?.salesExecutiveTxt = params?.salesExecutiveTxt
					customerInstance.errors.rejectValue('salesExecutiveTxt', 'org.solcorp.etech.Customer.salesExecutiveTxt.error.notFound')
				}
			}
			
			if(params?.industryTxt){
				def industry = Industry.findByCode(params?.industryTxt)
				if(industry){
					customerInstance?.industry = industry
					customerInstance.validate()
				}else{
					customerInstance?.industryTxt = params?.industryTxt
					customerInstance.errors.rejectValue('industryTxt', 'org.solcorp.etech.Customer.industryTxt.error.notFound')
				}
			}
			
			if(customerInstance.hasErrors()) {
				render(view: 'create', model: [customerInstance: customerInstance])
				return
			}
			def address = new Address(params)
			address.addressType = AddressType.BILLING // set address as Billing default
			customerInstance.addToAddresses(address)
			customerInstance.save flush: true
				
			flash.message = message(code: 'default.added.success.message', default: "Record added successfully")
			redirect(action: "edit", id: customerInstance.id)
			
		} catch(Exception e) {
			flash.message = message(code: 'default.general.error.save.message', null)
			redirect(action: "create", id: customerInstance.id)
			return
		}
	}

	def edit(Customer customerInstance) {
		
		def address = Address.createCriteria().list {
			createAlias("customer", "customer")
			and {
				eq("addressType", AddressType.BILLING)
				eq("customer.id", customerInstance.id)
			}
		}
		
		def contact = Contact.createCriteria().list {
			createAlias("customer", "customer")
			and {
				eq("type", ContactType.BILLING)
				eq("customer.id", customerInstance.id)
			}
		}
		render(view: 'edit', model: [customerInstance: customerInstance, addressInstance: address[0], contactInstance: contact[0]])
	}

	@Transactional
	def update(Customer customerInstance) { 
		try {
			
			if (customerInstance == null) {
				notFound()
				return
			}
	
			if (customerInstance.hasErrors()) {
				
				def address = Address.createCriteria().list {
					createAlias("customer", "customer")
					and {
						eq("addressType", AddressType.BILLING)
						eq("customer.id", customerInstance.id)
					}
				}
				
				respond customerInstance.errors, view: 'edit', model: [addressInstance: address[0]]
				return
			}
			
			def address = Address.findById(params?.address?.id)
			if(!address) {
				address = new Address()
			}
			address.addressType = AddressType.BILLING
			address.line1 = params?.line1
			address.line2 = params?.line2
			address.city = params?.city
			address.state = (params?.state.isEmpty()) ? null : params.state
			address.zip = params?.zip
			address.country = params?.country
		
			customerInstance.addToAddresses(address)
			
			customerInstance.save flush:true
		
			flash.message = message(code: 'default.updated.success.message', default: "Record updated successfully")
			redirect(action: "edit", id: customerInstance.id)
	   } catch(Exception e) {
		   log.error "Exception " + e
		   flash.message = message(code: 'default.general.error.save.message', null)
		   redirect(action: "edit", id: customerInstance.id)
		   return
	   }

	}

	@Transactional
	def delete(Customer customerInstance) {
		try {
			
			if (customerInstance == null) {
				notFound()
				return
			}
		
			customerInstance.delete flush:true, failOnError:true
			
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			redirect action:"create", method:"GET"
			return
			
		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
		
			flash.message = message(code: 'org.solcorp.etech.Customer.delete.child.ref.exists.error')
		} catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: customerInstance.id)
		return
	}
	
	/**
	 * This action used to search record from DB by Form parameter
	 * @return
	 */
	def list() {
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = customerService.getCustomerList(params)
			if(request.xhr) {
				
				render(template:"listRecords", model:modelMap)
			} else {
						
				render(view : "list", model : modelMap)
			}
				
			return
		} catch(Exception e) {
			log.error "Exception " + e
		}
	}
	
	
	def getBillingRecords(id) {
		
		def address = Address.createCriteria().list {
			createAlias("customer", "customer")
			and {
				projections {  
					property("country")
				}
				eq("addressType", AddressType.BILLING)
				eq("customer.id", id)
			}
		}
		
		def contact = Contact.createCriteria().list {
			createAlias("customer", "customer")
			and {
				projections {
					property("name")
				}
				eq("type", ContactType.BILLING)
				eq("customer.id", id)
			}
		}
		
		[address: address[0], contact: contact[0]]
	}

	def getHHCustomerMasterList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "customerImportList" , model: customerService.getHHCustomerMstList(params), layout: "ajax")
	}
	
	def getSalesExecutiveList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "ajaxSalesList" , model: customerService.getSalesExecutiveList(params), layout: "ajax")
	}
	
	def getIndustryList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "ajaxIndustryList" , model: customerService.getIndustryList(params), layout: "ajax")
	}
	
	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerInstance.label', default: 'Customer'), params.id])
				redirect action: "list", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
