import org.solcorp.etech.DESCodec;

dataSource {
    pooled = true
    //driverClassName = "com.mysql.jdbc.Driver"
	driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
	dbCreate  = "update"
	passwordEncryptionCodec = DESCodec
	properties {
		maxActive = -1
		minEvictableIdleTimeMillis=1800000
		timeBetweenEvictionRunsMillis=1800000
		numTestsPerEvictionRun=3
		testOnBorrow=true
		testWhileIdle=true
		testOnReturn=false
		validationQuery="SELECT 1"
		jdbcInterceptors="ConnectionState"
	 }
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
	//cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
	format_sql = true
}

// environment specific settings
environments {
    development {
		dataSource_hh {
			pooled = true
			dialect = org.hibernate.dialect.Oracle10gDialect
			driverClassName = 'oracle.jdbc.driver.OracleDriver'
			//driverClassName = "oracle.jdbc.driver.OracleDriver"
			url='jdbc:oracle:thin:@Psdev.harte-hanks.com:1521:DEV'
			username = "Costing_usr"
			password = "3gK5iYyzsnWL/Uf42+fWAg=="
			dbCreate  = "update"
			passwordEncryptionCodec = DESCodec
			readOnly = "true"
			properties {
				maxActive = -1
				minEvictableIdleTimeMillis=1800000
				timeBetweenEvictionRunsMillis=1800000
				numTestsPerEvictionRun=3
				testOnBorrow=true
				testWhileIdle=true
				testOnReturn=false
				validationQuery="SELECT 1"
				jdbcInterceptors="ConnectionState"
			 }
		}
        dataSource {
            //url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
            url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
      			username = "Costing_Usr"
      			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
        }
    }

	test {
        dataSource {
            //url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
            url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
            username = "Costing_Usr"
            password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
        }
    }

	qa {
		dataSource {
			url='jdbc:mysql://v2test.solutioncorp.com:3306/costing?useUnicode=yes&characterEncoding=UTF-8'
			username = "scdcdev1"
			password = "LjzjTJO9M6wt8cwE+fjKAg=="
		}
	}

	hh_stage {

		dataSource_hh {
		 pooled = true
		 dialect = org.hibernate.dialect.Oracle10gDialect
		 driverClassName = 'oracle.jdbc.driver.OracleDriver'
		 //driverClassName = "oracle.jdbc.driver.OracleDriver"
		 dbCreate  = "update"
		 passwordEncryptionCodec = DESCodec
		 readOnly = "true"
		 url='jdbc:oracle:thin:@Psdev.harte-hanks.com:1521:DEV'
		 username = "Costing_usr"
		 password = "3gK5iYyzsnWL/Uf42+fWAg=="
		 properties {
			 maxActive = -1
			 minEvictableIdleTimeMillis=1800000
			 timeBetweenEvictionRunsMillis=1800000
			 numTestsPerEvictionRun=3
			 testOnBorrow=true
			 testWhileIdle=true
			 testOnReturn=false
			 validationQuery="SELECT 1"
			 jdbcInterceptors="ConnectionState"
		  }
		}

		dataSource {
			//url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
      url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
			username = "Costing_Usr"
			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
		}
	}

	hh_prod {

		dataSource_hh {
			pooled = true
			dialect = org.hibernate.dialect.Oracle10gDialect
			driverClassName = 'oracle.jdbc.driver.OracleDriver'
			//driverClassName = "oracle.jdbc.driver.OracleDriver"
			dbCreate  = "update"
			passwordEncryptionCodec = DESCodec
			readOnly = "true"
			url='jdbc:oracle:thin:@psprod.harte-hanks.com:1521:FPRD'
			username = "Costing_usr"
			password = "LFRxPt83cCTx7EQPWK3nMg=="
			properties {
				maxActive = -1
				minEvictableIdleTimeMillis=1800000
				timeBetweenEvictionRunsMillis=1800000
				numTestsPerEvictionRun=3
				testOnBorrow=true
				testWhileIdle=true
				testOnReturn=false
				validationQuery="SELECT 1"
				jdbcInterceptors="ConnectionState"
			 }
		}
		dataSource {
			//url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
      url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
			username = "Costing_Usr"
			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
		}
	}

    production {
        dataSource {
           //url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
           url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
     			username = "Costing_Usr"
     			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
        }
    }
}
