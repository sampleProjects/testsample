String dynamicDate = new Date().toString().replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "-")

environments {	
	
	development {
		
		modules = {
			
			application {
				resource url: 'js/application.js', bundle: 'application-js-' + dynamicDate
			}
			
			common {
				dependsOn 'jquery'
				resource url: 'js/scrolltable.js', disposition: 'head', bundle: 'scrolltable-js-' + dynamicDate
				resource url: 'js/bootstrap.min.js', disposition: 'head', bundle: 'bootstrap-js-' + dynamicDate
				resource url: 'js/moment-with-locales.js', disposition: 'head', bundle: 'moment-js-' + dynamicDate
				resource url: 'js/bootstrap-datetimepicker.js', disposition: 'head', bundle: 'datetimepicker-js-' + dynamicDate
				//resource url: 'js/bootstrap-select.js', disposition: 'head', bundle: 'bootstrap-select'
				resource url: 'js/general.js', disposition: 'head', bundle: 'general-js-' + dynamicDate
				resource url: 'js/scriptbreaker-multiple-accordion-1.js', disposition: 'head', bundle: 'scriptbreaker-js-' + dynamicDate
				resource url: 'js/jquery.mCustomScrollbar.js', disposition: 'head', bundle: 'jCustomerScroll-js-' + dynamicDate
				resource url: 'js/jquery.are-you-sure.js', disposition: 'head', bundle: 'jquery-are-you-sure-js-' + dynamicDate
				
				resource url: 'css/bootstrap.min.css', disposition: 'head', bundle: 'bootstrap-css-' + dynamicDate			
				resource url: 'css/bootstrap-datetimepicker.css', disposition: 'head', bundle: 'datetimepicker-css-' + dynamicDate
				//resource url: 'css/bootstrap-select.css', disposition: 'head', bundle: 'bootstrap-select'
				resource url: 'css/font-awesome.css', disposition: 'head', bundle: 'awesome-css-' + dynamicDate
				resource url: 'css/jquery.mCustomScrollbar.css', disposition: 'head', bundle: 'mcustomerScrollbar-css-' + dynamicDate
				resource url: 'css/bootstrap-override.css', disposition: 'head', bundle: 'bootstrap-override-css-' + dynamicDate
				resource url: 'css/style.css', disposition: 'head', bundle: 'style-css-' + dynamicDate				
				resource url: 'css/typography.css', disposition: 'head', bundle: 'typography-css-' + dynamicDate
				
				
				resource url: 'css/layout-tabletxl.css', disposition: 'head', bundle: 'tabletxl-css-' + dynamicDate
				resource url: 'css/layout-tablet.css', disposition: 'head', bundle: 'tablet-css-' + dynamicDate
				resource url: 'css/layout-mobile.css', disposition: 'head', bundle: 'mobile-css-' + dynamicDate
				
											
			} 
			
			employee {
				dependsOn 'common'				
				resource url: 'js/employee/form.js', disposition: 'head', bundle: 'employee-' + dynamicDate
			}
			
			user {
				dependsOn 'common'
				resource url: 'js/user/form.js', disposition: 'head', bundle: 'user-' + dynamicDate
			}
			
			laborActivityGroup{
				dependsOn 'common'
				resource url: 'js/laborActivityGroup/form.js', disposition: 'head', bundle: 'laborActivityGroup-' + dynamicDate
			}
			
			laborActivityCode{
				dependsOn 'common'
				resource url: 'js/laborActivityCode/form.js', disposition: 'head', bundle: 'laborActivityCode-' + dynamicDate
			}
			 
			customer {
				dependsOn 'common'
				resource url: 'js/customer/form.js', disposition: 'head', bundle: 'customer-' + dynamicDate
			}
			
			address {
				dependsOn 'common'
				resource url: 'js/address/form.js', disposition: 'head', bundle: 'address-' + dynamicDate
			}
			 
			contact {
				dependsOn 'common'
				resource url: 'js/contact/form.js', disposition: 'head', bundle: 'contact-' + dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'contact1-' + dynamicDate
			}
			
			options {
				dependsOn 'common'
				resource url: 'js/options/form.js', disposition: 'head', bundle: 'options-' + dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'options1-' + dynamicDate
			}
			
			images {
				resource url: 'images/grails_logo.png', attrs: [width: 400, height: 180, alt: 'logo.'], disposition: 'image'
			}
			
			product{
				dependsOn 'common'
				resource url: 'js/product/form.js', disposition: 'head', bundle: 'product-' + dynamicDate
			}
			
			project {
				dependsOn 'common'
				resource url: 'js/project/form.js', disposition: 'head', bundle: 'project-' + dynamicDate
			}
			
			projectLabor {
				dependsOn 'common'
				resource url: 'js/projectLabor/form.js', disposition: 'head', bundle: 'projectLabor-' + dynamicDate
			}
			
			projectExpense {
				dependsOn 'common'
				resource url: 'js/projectExpense/form.js', disposition: 'head', bundle: 'projectExpense-' + dynamicDate
			}
			
			department {
				dependsOn 'common'
				resource url: 'js/department/form.js', disposition: 'head', bundle: 'Department-' + dynamicDate
			}
			
			report {
				dependsOn 'common'
				resource url: 'js/report/form.js', disposition: 'head', bundle: 'report-' + dynamicDate
			}
		}
	}
	
	qa {
		
		modules = {
			
			application {
				resource url: 'js/application.js'
			}
			
			common {
				dependsOn 'jquery'
				resource url: 'js/scrolltable.js', disposition: 'head'
				resource url: 'js/bootstrap.min.js', disposition: 'head'
				resource url: 'js/moment-with-locales.js', disposition: 'head'
				resource url: 'js/bootstrap-datetimepicker.js', disposition: 'head'
				//resource url: 'js/bootstrap-select.js', disposition: 'head'
				resource url: 'js/general.js', disposition: 'head'
				resource url: 'js/scriptbreaker-multiple-accordion-1.js', disposition: 'head'
				resource url: 'js/jquery.mCustomScrollbar.js', disposition: 'head'
				resource url: 'js/jquery.are-you-sure.js', disposition: 'head', bundle: 'jquery-are-you-sure-js-' + dynamicDate
				
				resource url: 'css/bootstrap.min.css', disposition: 'head'
				resource url: 'css/bootstrap-datetimepicker.css', disposition: 'head'
				//resource url: 'css/bootstrap-select.css', disposition: 'head'
				resource url: 'css/font-awesome.css', disposition: 'head'
				resource url: 'css/jquery.mCustomScrollbar.css', disposition: 'head'
				resource url: 'css/bootstrap-override.css', disposition: 'head'
				resource url: 'css/style.css', disposition: 'head'				
				resource url: 'css/typography.css', disposition: 'head'				
								
				resource url: 'css/layout-tabletxl.css', disposition: 'head'
				resource url: 'css/layout-tablet.css', disposition: 'head'
				resource url: 'css/layout-mobile.css', disposition: 'head'
			}
			
			overrides {
				common {
					defaultBundle 'etech-'+ dynamicDate
				}
			}
				
			employee {
				dependsOn 'common'
				resource url: 'js/employee/form.js', disposition: 'head', bundle: 'emp_etech-'+ dynamicDate
			}
			user {
				dependsOn 'common'
				resource url: 'js/user/form.js', disposition: 'head', bundle: 'user_etech-'+ dynamicDate
			}
			
			laborActivityGroup{
				dependsOn 'common'
				resource url: 'js/laborActivityGroup/form.js', disposition: 'head', bundle: 'laborActivityGroup-'+ dynamicDate
			}
			
			laborActivityCode{
				dependsOn 'common'
				resource url: 'js/laborActivityCode/form.js', disposition: 'head', bundle: 'laborActivityCode-'+ dynamicDate
			}
			
			customer {
				dependsOn 'common'
				resource url: 'js/customer/form.js', disposition: 'head', bundle: 'customer-'+ dynamicDate
			}
			
			address {
				dependsOn 'common'
				resource url: 'js/address/form.js', disposition: 'head', bundle: 'address-'+ dynamicDate
			}
			 
			contact {
				dependsOn 'common'
				resource url: 'js/contact/form.js', disposition: 'head', bundle: 'contact-'+ dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'contact1-'+ dynamicDate
			}
			
			options {
				dependsOn 'common'
				resource url: 'js/options/form.js', disposition: 'head', bundle: 'options-'+ dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'options1-'+ dynamicDate
			}
			
			images {
				resource url: 'images/grails_logo.png', attrs: [width: 400, height: 180, alt: 'logo.'], disposition: 'image'
			}
			
			product{
				dependsOn 'common'
				resource url: 'js/product/form.js', disposition: 'head', bundle: 'product-'+ dynamicDate
			}
			project {
				dependsOn 'common'
				resource url: 'js/project/form.js', disposition: 'head', bundle: 'project-'+ dynamicDate
			}
			
			projectLabor {
				dependsOn 'common'
				resource url: 'js/projectLabor/form.js', disposition: 'head', bundle: 'projectLabor-'+ dynamicDate
			}
			
			projectExpense {
				dependsOn 'common'
				resource url: 'js/projectExpense/form.js', disposition: 'head', bundle: 'projectExpense-'+ dynamicDate
			}
			
			department {
				dependsOn 'common'
				resource url: 'js/department/form.js', disposition: 'head', bundle: 'Department-' + dynamicDate
			}
			
			report {
				dependsOn 'common'
				resource url: 'js/report/form.js', disposition: 'head', bundle: 'report-' + dynamicDate
			}
		}
	}
	
	hh_stage {
		
		modules = {
			
			application {
				resource url: 'js/application.js'
			}
			
			common {
				dependsOn 'jquery'
				resource url: 'js/scrolltable.js', disposition: 'head'
				resource url: 'js/bootstrap.min.js', disposition: 'head'
				resource url: 'js/moment-with-locales.js', disposition: 'head'
				resource url: 'js/bootstrap-datetimepicker.js', disposition: 'head'
				//resource url: 'js/bootstrap-select.js', disposition: 'head'
				resource url: 'js/general.js', disposition: 'head'
				resource url: 'js/scriptbreaker-multiple-accordion-1.js', disposition: 'head'
				resource url: 'js/jquery.mCustomScrollbar.js', disposition: 'head'
				resource url: 'js/jquery.are-you-sure.js', disposition: 'head', bundle: 'jquery-are-you-sure-js-' + dynamicDate
				
				resource url: 'css/bootstrap.min.css', disposition: 'head'
				resource url: 'css/bootstrap-datetimepicker.css', disposition: 'head'
				//resource url: 'css/bootstrap-select.css', disposition: 'head'
				resource url: 'css/font-awesome.css', disposition: 'head'
				resource url: 'css/jquery.mCustomScrollbar.css', disposition: 'head'
				resource url: 'css/bootstrap-override.css', disposition: 'head'
				resource url: 'css/style.css', disposition: 'head'				
				resource url: 'css/typography.css', disposition: 'head'				
								
				resource url: 'css/layout-tabletxl.css', disposition: 'head'
				resource url: 'css/layout-tablet.css', disposition: 'head'
				resource url: 'css/layout-mobile.css', disposition: 'head'
			}
			
			overrides {
				common {
					defaultBundle 'etech-'+ dynamicDate
				}
			}
				
			employee {
				dependsOn 'common'
				resource url: 'js/employee/form.js', disposition: 'head', bundle: 'emp_etech-'+ dynamicDate
			}
			user {
				dependsOn 'common'
				resource url: 'js/user/form.js', disposition: 'head', bundle: 'user_etech-'+ dynamicDate
			}
			
			laborActivityGroup{
				dependsOn 'common'
				resource url: 'js/laborActivityGroup/form.js', disposition: 'head', bundle: 'laborActivityGroup-'+ dynamicDate
			}
			
			laborActivityCode{
				dependsOn 'common'
				resource url: 'js/laborActivityCode/form.js', disposition: 'head', bundle: 'laborActivityCode-'+ dynamicDate
			}
			
			customer {
				dependsOn 'common'
				resource url: 'js/customer/form.js', disposition: 'head', bundle: 'customer-'+ dynamicDate
			}
			
			address {
				dependsOn 'common'
				resource url: 'js/address/form.js', disposition: 'head', bundle: 'address-'+ dynamicDate
			}
			 
			contact {
				dependsOn 'common'
				resource url: 'js/contact/form.js', disposition: 'head', bundle: 'contact-'+ dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'contact1-'+ dynamicDate
			}
			
			options {
				dependsOn 'common'
				resource url: 'js/options/form.js', disposition: 'head', bundle: 'options-'+ dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'options1-'+ dynamicDate
			}
			
			images {
				resource url: 'images/grails_logo.png', attrs: [width: 400, height: 180, alt: 'logo.'], disposition: 'image'
			}
			
			product{
				dependsOn 'common'
				resource url: 'js/product/form.js', disposition: 'head', bundle: 'product-'+ dynamicDate
			}
			project {
				dependsOn 'common'
				resource url: 'js/project/form.js', disposition: 'head', bundle: 'project-'+ dynamicDate
			}
			
			projectLabor {
				dependsOn 'common'
				resource url: 'js/projectLabor/form.js', disposition: 'head', bundle: 'projectLabor-'+ dynamicDate
			}
			
			projectExpense {
				dependsOn 'common'
				resource url: 'js/projectExpense/form.js', disposition: 'head', bundle: 'projectExpense-'+ dynamicDate
			}
			
			department {
				dependsOn 'common'
				resource url: 'js/department/form.js', disposition: 'head', bundle: 'Department-' + dynamicDate
			}
			
			report {
				dependsOn 'common'
				resource url: 'js/report/form.js', disposition: 'head', bundle: 'report-' + dynamicDate
			}
		}
	}
	
	hh_prod {
		
		modules = {
			
			application {
				resource url: 'js/application.js'
			}
			
			common {
				dependsOn 'jquery'
				resource url: 'js/scrolltable.js', disposition: 'head'
				resource url: 'js/bootstrap.min.js', disposition: 'head'
				resource url: 'js/moment-with-locales.js', disposition: 'head'
				resource url: 'js/bootstrap-datetimepicker.js', disposition: 'head'
				//resource url: 'js/bootstrap-select.js', disposition: 'head'
				resource url: 'js/general.js', disposition: 'head'
				resource url: 'js/scriptbreaker-multiple-accordion-1.js', disposition: 'head'
				resource url: 'js/jquery.mCustomScrollbar.js', disposition: 'head'
				resource url: 'js/jquery.are-you-sure.js', disposition: 'head', bundle: 'jquery-are-you-sure-js-' + dynamicDate
				
				resource url: 'css/bootstrap.min.css', disposition: 'head'
				resource url: 'css/bootstrap-datetimepicker.css', disposition: 'head'
				//resource url: 'css/bootstrap-select.css', disposition: 'head'
				resource url: 'css/font-awesome.css', disposition: 'head'
				resource url: 'css/jquery.mCustomScrollbar.css', disposition: 'head'
				resource url: 'css/bootstrap-override.css', disposition: 'head'
				resource url: 'css/style.css', disposition: 'head'
				resource url: 'css/typography.css', disposition: 'head'
								
				resource url: 'css/layout-tabletxl.css', disposition: 'head'
				resource url: 'css/layout-tablet.css', disposition: 'head'
				resource url: 'css/layout-mobile.css', disposition: 'head'
			}
			
			overrides {
				common {
					defaultBundle 'etech-'+ dynamicDate
				}
			}
				
			employee {
				dependsOn 'common'
				resource url: 'js/employee/form.js', disposition: 'head', bundle: 'emp_etech-'+ dynamicDate
			}
			user {
				dependsOn 'common'
				resource url: 'js/user/form.js', disposition: 'head', bundle: 'user_etech-'+ dynamicDate
			}
			
			laborActivityGroup{
				dependsOn 'common'
				resource url: 'js/laborActivityGroup/form.js', disposition: 'head', bundle: 'laborActivityGroup-'+ dynamicDate
			}
			
			laborActivityCode{
				dependsOn 'common'
				resource url: 'js/laborActivityCode/form.js', disposition: 'head', bundle: 'laborActivityCode-'+ dynamicDate
			}
			
			customer {
				dependsOn 'common'
				resource url: 'js/customer/form.js', disposition: 'head', bundle: 'customer-'+ dynamicDate
			}
			
			address {
				dependsOn 'common'
				resource url: 'js/address/form.js', disposition: 'head', bundle: 'address-'+ dynamicDate
			}
			 
			contact {
				dependsOn 'common'
				resource url: 'js/contact/form.js', disposition: 'head', bundle: 'contact-'+ dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'contact1-'+ dynamicDate
			}
			
			options {
				dependsOn 'common'
				resource url: 'js/options/form.js', disposition: 'head', bundle: 'options-'+ dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'options1-'+ dynamicDate
			}
			
			images {
				resource url: 'images/grails_logo.png', attrs: [width: 400, height: 180, alt: 'logo.'], disposition: 'image'
			}
			
			product{
				dependsOn 'common'
				resource url: 'js/product/form.js', disposition: 'head', bundle: 'product-'+ dynamicDate
			}
			project {
				dependsOn 'common'
				resource url: 'js/project/form.js', disposition: 'head', bundle: 'project-'+ dynamicDate
			}
			
			projectLabor {
				dependsOn 'common'
				resource url: 'js/projectLabor/form.js', disposition: 'head', bundle: 'projectLabor-'+ dynamicDate
			}
			
			projectExpense {
				dependsOn 'common'
				resource url: 'js/projectExpense/form.js', disposition: 'head', bundle: 'projectExpense-'+ dynamicDate
			}
			
			department {
				dependsOn 'common'
				resource url: 'js/department/form.js', disposition: 'head', bundle: 'Department-' + dynamicDate
			}
			
			report {
				dependsOn 'common'
				resource url: 'js/report/form.js', disposition: 'head', bundle: 'report-' + dynamicDate
			}
		}
	}
	production {
		
		modules = {
			
			application {
				resource url: 'js/application.js'
			}
			
			common {
				dependsOn 'jquery'
				resource url: 'js/scrolltable.js', disposition: 'head'
				resource url: 'js/bootstrap.min.js', disposition: 'head'
				resource url: 'js/moment-with-locales.js', disposition: 'head'
				resource url: 'js/bootstrap-datetimepicker.js', disposition: 'head'
				//resource url: 'js/bootstrap-select.js', disposition: 'head'
				resource url: 'js/general.js', disposition: 'head'
				resource url: 'js/scriptbreaker-multiple-accordion-1.js', disposition: 'head'
				resource url: 'js/jquery.mCustomScrollbar.js', disposition: 'head'
				resource url: 'js/jquery.are-you-sure.js', disposition: 'head', bundle: 'jquery-are-you-sure-js-' + dynamicDate
				
				resource url: 'css/bootstrap.min.css', disposition: 'head'
				resource url: 'css/bootstrap-datetimepicker.css', disposition: 'head'
				//resource url: 'css/bootstrap-select.css', disposition: 'head'
				resource url: 'css/font-awesome.css', disposition: 'head'
				resource url: 'css/jquery.mCustomScrollbar.css', disposition: 'head'
				resource url: 'css/bootstrap-override.css', disposition: 'head'
				resource url: 'css/style.css', disposition: 'head'				
				resource url: 'css/typography.css', disposition: 'head'				
								
				resource url: 'css/layout-tabletxl.css', disposition: 'head'
				resource url: 'css/layout-tablet.css', disposition: 'head'
				resource url: 'css/layout-mobile.css', disposition: 'head'
			}
			
			overrides {
				common {
					defaultBundle 'etech-'+ dynamicDate
				}
			}
				
			employee {
				dependsOn 'common'
				resource url: 'js/employee/form.js', disposition: 'head', bundle: 'emp_etech-'+ dynamicDate
			}
			user {
				dependsOn 'common'
				resource url: 'js/user/form.js', disposition: 'head', bundle: 'user_etech-'+ dynamicDate
			}
			
			laborActivityGroup{
				dependsOn 'common'
				resource url: 'js/laborActivityGroup/form.js', disposition: 'head', bundle: 'laborActivityGroup-'+ dynamicDate
			}
			
			laborActivityCode{
				dependsOn 'common'
				resource url: 'js/laborActivityCode/form.js', disposition: 'head', bundle: 'laborActivityCode-'+ dynamicDate
			}
			
			customer {
				dependsOn 'common'
				resource url: 'js/customer/form.js', disposition: 'head', bundle: 'customer-'+ dynamicDate
			}
			
			address {
				dependsOn 'common'
				resource url: 'js/address/form.js', disposition: 'head', bundle: 'address-'+ dynamicDate
			}
			 
			contact {
				dependsOn 'common'
				resource url: 'js/contact/form.js', disposition: 'head', bundle: 'contact-'+ dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'contact1-'+ dynamicDate
			}
			
			options {
				dependsOn 'common'
				resource url: 'js/options/form.js', disposition: 'head', bundle: 'options-'+ dynamicDate
				resource url: 'js/jquery.mask.min.js', bundle: 'options1-'+ dynamicDate
			}
			
			images {
				resource url: 'images/grails_logo.png', attrs: [width: 400, height: 180, alt: 'logo.'], disposition: 'image'
			}
			
			product{
				dependsOn 'common'
				resource url: 'js/product/form.js', disposition: 'head', bundle: 'product-'+ dynamicDate
			}
			project {
				dependsOn 'common'
				resource url: 'js/project/form.js', disposition: 'head', bundle: 'project-'+ dynamicDate
			}
			
			projectLabor {
				dependsOn 'common'
				resource url: 'js/projectLabor/form.js', disposition: 'head', bundle: 'projectLabor-'+ dynamicDate
			}
			
			projectExpense {
				dependsOn 'common'
				resource url: 'js/projectExpense/form.js', disposition: 'head', bundle: 'projectExpense-'+ dynamicDate
			}
			
			department {
				dependsOn 'common'
				resource url: 'js/department/form.js', disposition: 'head', bundle: 'Department-' + dynamicDate
			}
			
			report {
				dependsOn 'common'
				resource url: 'js/report/form.js', disposition: 'head', bundle: 'report-' + dynamicDate
			}
		}
	}
}