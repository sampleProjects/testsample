class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(action:"dashboard", controller:"auth")
        "500"(view:'/error')
		
	}
}
