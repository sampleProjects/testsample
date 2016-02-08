package org.solcorp.etech

class MiscellaneousCategory extends ProductCategory {

	static mapping = {
		discriminator value: ProductClassType.MISCELLANEOUS
	}
}
