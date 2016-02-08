package org.solcorp.etech

import org.solcorp.etech.RoleType
import org.solcorp.etech.PermissionType

class RoleMaintenanceController {
	
	static allowedMethods = [save : "POST", update : "PUT"]
	
	def clear() {
		redirect action: "list"
	}
	
	def list() {
		try {
			flash.clear()
			def roleList = Role.list()
			render(view : "create", model:[roleMaintenanceInstance: roleList])
			return
		}catch(Exception e) {
			log.error "Role Maintenance List Exception "+e
			def roleList = Role.list()
			render(view : "create", model:[roleMaintenanceInstance: roleList])
		}
	}
	
	def save(){
		try {
			flash.clear()
			
			def roleList = Role?.list()
			def constPermissionList = PermissionType?.permissionList()*.name()
			def constRoleList = RoleType?.roleListForPermissions()*.name()
			
			if(roleList){
				for(int i=0;i<roleList.size();i++){
					if(constRoleList.contains(roleList[i]?.name)){
						for(int j=0;j<constPermissionList.size();j++){
							String keyName = "chk_"+roleList[i]?.name+"_"+constPermissionList[j]
							if (params.containsKey(keyName)) {
								if(!roleList[i]?.permissions?.contains(constPermissionList[j])){
									roleList[i]?.addToPermissions(constPermissionList[j])
								}
							}else{
								if(roleList[i]?.permissions?.contains(constPermissionList[j])){
									roleList[i]?.removeFromPermissions(constPermissionList[j])
								}
							}
						}
					}
					roleList[i].save(flush: true, saveOnError: true);
				}
			}
			flash.message = message(code: 'default.updated.success.message', default :"Record updated successfully")
			render(view : "create", model:[roleMaintenanceInstance: roleList])
			return
		}catch(Exception e) {
			log.error "Role Maintenance Save Exception "+e
			flash.message = message(code: 'default.general.error.save.message', null)
			def roleList = Role.list()
			render(view : "create", model:[roleMaintenanceInstance: roleList])
		}
	}
	
	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code : 'default.not.found.message', args : [message(code : 'roleMaintenanceInstance.label', default : 'Role Maintenance'), params?.id])
				redirect action : "list", method : "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
