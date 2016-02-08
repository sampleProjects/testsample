// Place your Spring DSL code here
beans = {
	 
	customSessionListener(org.solcorp.etech.listeners.CustomSessionListener){
		userSessionService = ref('userSessionService')
	}
}
