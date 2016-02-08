package org.solcorp.etech

class ProjectProductCategory extends ProductCategory {

    static mapping = {
        discriminator value: ProductClassType.PROJECT
    }
}
