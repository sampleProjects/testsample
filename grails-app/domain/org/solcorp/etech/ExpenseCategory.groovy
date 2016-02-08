package org.solcorp.etech

class ExpenseCategory extends ProductCategory {

   static mapping = {
		discriminator value: ProductClassType.EXPENSES
   }
}
