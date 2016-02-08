class BootStrap {
	def initService
	def customSessionListener
	
    def init = { servletContext ->
		initService.bootstrapData()	
		servletContext.addListener(customSessionListener)
    }
	
    def destroy = {
    }
}
