// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.gsp.enable.reload = true
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.resources.resourceLocatorEnabled = true
environments {
	development {
		def ENV_NAME = "ETECH_CONFIG"
		if(!grails.config.locations || !(grails.config.locations instanceof List)) {
			grails.config.locations = []
		}
		def envVarConfig = null
		config.scayt_autoStartup = true;
		
		if(System.getenv(ENV_NAME)) {
			println "Including configuration file specified in environment: " + System.getenv(ENV_NAME);
			envVarConfig = "file:" + System.getenv(ENV_NAME)
		} else if(System.getProperty(ENV_NAME)) {
			println "Including configuration file specified on command line: " + System.getProperty(ENV_NAME);
			envVarConfig = "file:" + System.getProperty(ENV_NAME)
		} else {
			println "No external configuration file defined.  If you need an external configuration file, define it in the environment variable (or command line argument) ETECH_CONFIG"
		}
		
		if(envVarConfig) {
			grails.config.locations << envVarConfig
			
			grails.plugins.reloadConfig.notifyPlugins = ["dataSource", "hibernate", "sharding"]
		}
	}
	qa {
		def ENV_NAME = "ETECH_CONFIG"
		if(!grails.config.locations || !(grails.config.locations instanceof List)) {
			grails.config.locations = []
		}
		def envVarConfig = null
		config.scayt_autoStartup = true;
		
		if(System.getenv(ENV_NAME)) {
			println "Including configuration file specified in environment: " + System.getenv(ENV_NAME);
			envVarConfig = "file:" + System.getenv(ENV_NAME)
		} else if(System.getProperty(ENV_NAME)) {
			println "Including configuration file specified on command line: " + System.getProperty(ENV_NAME);
			envVarConfig = "file:" + System.getProperty(ENV_NAME)
		} else {
			println "No external configuration file defined.  If you need an external configuration file, define it in the environment variable (or command line argument) ETECH_CONFIG"
		}
		
		if(envVarConfig) {
			grails.config.locations << envVarConfig
			
			grails.plugins.reloadConfig.notifyPlugins = ["dataSource", "hibernate", "sharding"]
		}
	}
	hh_stage {
		grails.plugins.reloadConfig.notifyPlugins = ["dataSource", "hibernate", "sharding"]
	}
	hh_prod {
		grails.plugins.reloadConfig.notifyPlugins = ["dataSource", "hibernate", "sharding"]
		grails.app.context='/'
	}
}
// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
 
grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}
etech.report.file.path = "/opt/etech/reports/"
// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
	
	
	def FILE_LOCATION = "/opt/etech/logs/"
	
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} %-5p %c %x - %m%n')
		rollingFile name: 'importCustomersJobAppender', layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} %-5p %c %x - %m%n'), file: FILE_LOCATION + "importCustomersJob.log"
		rollingFile name: 'importDepartmentsJobAppender', layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} %-5p %c %x - %m%n'), file: FILE_LOCATION + "importDepartmentsJob.log" 
		rollingFile name: 'importEmployeesJobAppender', layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} %-5p %c %x - %m%n'), file: FILE_LOCATION + "importEmployeesJob.log"
		rollingFile name: 'importProjectsJobAppender', layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} %-5p %c %x - %m%n'), file: FILE_LOCATION + "importProjectsJob.log"
		rollingFile name: 'importLaborTransactionsJobAppender', layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} %-5p %c %x - %m%n'), file: FILE_LOCATION + "importLaborTxnJob.log"
		rollingFile name: 'importExpensesJobAppender', layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} %-5p %c %x - %m%n'), file: FILE_LOCATION + "importExpensesJob.log"
		rollingFile name: 'syncHistoryWithLatestRatesAppender', layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} %-5p %x - %m%n'), file: FILE_LOCATION + "syncLaborHistory.log"
	}
	
    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
		   'org.solcorp.etech'
		   
   info additivity: false, 'importDepartmentsJobAppender': ["grails.app.services.org.solcorp.etech.DepartmentService", "grails.app.jobs.org.solcorp.etech.ImportDepartmentsJob"]
	  
   info additivity: false, 'importCustomersJobAppender': ["grails.app.services.org.solcorp.etech.HHCustomerMasterService", "grails.app.jobs.org.solcorp.etech.ImportCustomersJob"]
   
   info additivity: false, 'importEmployeesJobAppender': ["grails.app.services.org.solcorp.etech.HHEmployeeMasterService", "grails.app.jobs.org.solcorp.etech.ImportEmployeesJob"]
   
   info additivity: false, 'importProjectsJobAppender': ["grails.app.services.org.solcorp.etech.HHProjectMasterService", "grails.app.jobs.org.solcorp.etech.ImportProjectsJob", "grails.app.jobs.org.solcorp.etech.ImportMissingProjectsJob"]
  
   info additivity: false, 'importLaborTransactionsJobAppender': ["grails.app.services.org.solcorp.etech.HHLaborTransactionService", "grails.app.jobs.org.solcorp.etech.ImportLaborTransactionsJob"]
   
   info additivity: false, 'importExpensesJobAppender': ["grails.app.services.org.solcorp.etech.HHExpenseMasterService", "grails.app.jobs.org.solcorp.etech.ImportExpensesJob"]
   
   info additivity: false, 'syncHistoryWithLatestRatesAppender': ["grails.app.services.org.solcorp.etech.ProjectActualLaborService"]
   
   root {
	   info 'stdout'   
   }
   
}

// customized config parameters
etech.admin.dynamicpasswordlogic.use = true

security.shiro.authc.required = false

quartz.monitor.showCountdown = false

quartz.monitor.showTickingClock = false