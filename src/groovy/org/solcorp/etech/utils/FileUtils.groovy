package org.solcorp.etech.utils

import grails.util.Holders

class FileUtils {
	
	 
	def static createTempFile(String fileName, String extension){
		def filePath = Holders.config.etech.report.file.path
		createFolder(filePath)
		File directory = new File(filePath)
		return File.createTempFile(fileName, extension, directory)
		 			 
	}
	
	def static createFolder(String location){
		File path = new File(location)
		if(!path.exists()) {
			path.mkdirs()
		}
	}
	
}
