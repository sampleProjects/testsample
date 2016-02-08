environments {
    development {
        dataSource {
            //url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
			url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
			username = "Costing_usr"
			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
        }
		dataSource_hh{
			url='jdbc:oracle:thin:@Psdev.harte-hanks.com:1521:DEV'
			username = "Costing_usr"
			password = "3gK5iYyzsnWL/Uf42+fWAg=="
		}
    }

	test {
        dataSource {
            //url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
			url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
			username = "costing_usr"
			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
        }
		dataSource_hh{
			url='jdbc:oracle:thin:@Psdev.harte-hanks.com:1521:DEV'
			username = "Costing_usr"
			password = "3gK5iYyzsnWL/Uf42+fWAg=="
		}
    }

	qa {
		dataSource {
			url='jdbc:mysql://v2test.solutioncorp.com:3306/costing?useUnicode=yes&characterEncoding=UTF-8'
			//url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
			username = "costing_usr"
			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
		}
	}

	hh_stage {
		dataSource {
			//url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
			url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
			username = "Costing_Usr"
			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
		}
		dataSource_hh{
			url='jdbc:oracle:thin:@Psdev.harte-hanks.com:1521:DEV'
			username = "Costing_usr"
			password = "3gK5iYyzsnWL/Uf42+fWAg=="
		}
	}

	hh_prod {
		dataSource {
			//url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
			url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
			username = "Costing_Usr"
			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
		}
		dataSource_hh{
			url='jdbc:oracle:thin:@psprod.harte-hanks.com:1521:FPRD'
			username = "Costing_usr"
			password = "LFRxPt83cCTx7EQPWK3nMg=="
		}
	}

    production {
        dataSource {
           //url='jdbc:mysql://localhost:3306/etech_dev?useUnicode=yes&characterEncoding=UTF-8'
			url='jdbc:sqlserver://TESTSQL07.austx.ad.harte-hanks.com;databaseName=Costing'
			username = "Costing_usr"
			password = "se4r5fOSVYHwlr54/aFGSLAbSsUKI+iL"
        }
		dataSource_hh{
			url='jdbc:oracle:thin:@Psdev.harte-hanks.com:1521:DEV'
			username = "Costing_usr"
			password = "3gK5iYyzsnWL/Uf42+fWAg=="
		}
    }
}
