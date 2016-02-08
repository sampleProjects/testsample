package org.solcorp.etech

class ProjectEmployee extends Auditable {

	Long id
	
	Employee employee
	
	static belongsTo = [project: Project ]
	
    static constraints = {
		employee(nullable: false)
    }
}
