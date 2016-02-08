package org.solcorp.etech

class ServiceCategory extends ProductCategory {

    static mapping = {
		discriminator value: ProductClassType.SERVICES
	}
}
