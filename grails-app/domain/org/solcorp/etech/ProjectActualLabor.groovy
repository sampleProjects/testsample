package org.solcorp.etech

import java.util.Date;
import org.grails.databinding.BindingFormat;

class ProjectActualLabor extends Auditable {
	
	Long id
			
	List laborDetails = new ArrayList()
	
	static hasMany = [laborDetails: ProjectActualLaborDetail]
	
    static constraints = {
		
    }
}
