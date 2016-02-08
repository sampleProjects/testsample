<%@ page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.RoleType"%>
<%@ page import="org.solcorp.etech.PermissionType"%>
<div style="overflow-x:scroll; margin-left:130px; overflow-y:visible;padding-bottom:0px;">
			<table class="table table-striped text-center">
			<thead>
			<tr style="height:173px;">
				<th class="headcol" ><div class='rotate270' >Permissions</div>Roles</th>
				<g:if test="${PermissionType?.permissionList()}">
					<g:each in="${PermissionType?.permissionList()}" status="i" var="permissionList">
							<th  >${Constants.PERMISSION_LABEL_MAP.get(permissionList?.toString()) ?: permissionList}</th>
					</g:each>
				</g:if>
			</tr>
			</thead>
			
			<tbody>
			<g:if test="${RoleType?.roleListForPermissions()}">
				<g:set var="sortedRolesList" value="${RoleType?.roleListForPermissions()?.sort()}" />
				<g:each in="${sortedRolesList}" status="i" var="roleList">
					<tr>
						<th class="headcol">${roleList}</th>
						
						<g:if test="${roleMaintenanceInstance}">
							<g:each in="${roleMaintenanceInstance}" status="j" var="roleMainInstance">
								<g:if test="${roleMainInstance?.name?.equals(roleList?.name())}">
									<g:if test="${PermissionType?.permissionList()}">
										<g:each in="${PermissionType?.permissionList()}" status="k" var="permissionList">
											<g:if test="${roleMainInstance?.permissions?.contains(permissionList.name())}">
												<td align="center" ><input type="checkbox" name="chk_${roleMainInstance?.name}_${permissionList?.name()}" checked="checked" data-html="true" data-toggle="tooltip" data-original-title="Role: ${roleList} <br>Permission: ${Constants.PERMISSION_LABEL_MAP.get(permissionList?.toString())}" data-placement="top" /></td>
											</g:if>
											<g:else>
												<td align="center" ><input type="checkbox" name="chk_${roleMainInstance?.name}_${permissionList?.name()}" data-html="true" data-toggle="tooltip" data-original-title="Role: ${roleList} <br>Permission: ${Constants.PERMISSION_LABEL_MAP.get(permissionList?.toString())}" data-placement="top" /></td>
											</g:else>
										</g:each>
									</g:if>
								</g:if>
							</g:each>
						</g:if>

					</tr>
				</g:each>
			</g:if>
			</tbody>
			 
		</table>
</div>

