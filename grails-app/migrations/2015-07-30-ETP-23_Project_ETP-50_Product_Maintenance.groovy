databaseChangeLog = {

	changeSet(author: "tbthakker (generated)", id: "1438241534123-1") {
		createTable(tableName: "project_detail") {
			column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "project_detaiPK")
			}

			column(name: "version", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "actual_comp_date", type: "datetime")

			column(name: "actual_start_date", type: "datetime")

			column(name: "planned_labor_id", type: "numeric(19,0)")

			column(name: "project_id", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "project_product_id", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "revise_comp_date", type: "datetime")

			column(name: "revise_start_date", type: "datetime")

			column(name: "schedule_comp_date", type: "datetime")

			column(name: "schedule_start_date", type: "datetime")

			column(name: "service_id", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "project_details_idx", type: "int")
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-2") {
		createTable(tableName: "project_expense_detail") {
			column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "project_expenPK")
			}

			column(name: "version", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "expense_code_id", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "numeric(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "unit_cost", type: "numeric(19,2)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-3") {
		createTable(tableName: "project_labor") {
			column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "project_laborPK")
			}

			column(name: "version", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "numeric(19,0)")

			column(name: "date_created", type: "datetime")

			column(name: "labor_activity_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime")

			column(name: "project_labor_total", type: "numeric(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "updated_by_id", type: "numeric(19,0)")
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-4") {
		createTable(tableName: "project_labor_detail") {
			column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "project_laborPK")
			}

			column(name: "version", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "created_by_id", type: "numeric(19,0)")

			column(name: "date_created", type: "datetime")

			column(name: "hours", type: "numeric(19,2)")

			column(name: "labor_activity_code_id", type: "numeric(19,0)")

			column(name: "last_updated", type: "datetime")

			column(name: "over_head_cost", type: "numeric(19,2)")

			column(name: "project_labor_id", type: "numeric(19,0)")

			column(name: "standard_cost", type: "numeric(19,2)")

			column(name: "total_cost", type: "numeric(19,2)")

			column(name: "updated_by_id", type: "numeric(19,0)")
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-5") {
		addColumn(tableName: "project") {
			column(name: "acc_executive_id", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-6") {
		addColumn(tableName: "project") {
			column(name: "bill_code_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-7") {
		addColumn(tableName: "project") {
			column(name: "customer_id", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-8") {
		addColumn(tableName: "project") {
			column(name: "manager_id", type: "numeric(19,0)")
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-9") {
		addColumn(tableName: "project") {
			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-10") {
		addColumn(tableName: "project") {
			column(name: "parent_project_id", type: "numeric(19,0)")
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-11") {
		addColumn(tableName: "project") {
			column(name: "project_category_id", type: "numeric(19,0)")
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-12") {
		addColumn(tableName: "project") {
			column(name: "project_lead_id", type: "numeric(19,0)")
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-13") {
		addColumn(tableName: "project") {
			column(name: "project_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-14") {
		dropForeignKeyConstraint(baseTableName: "project_services", baseTableSchemaName: "dbo", constraintName: "FKB5A62C450B45B49")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-15") {
		dropForeignKeyConstraint(baseTableName: "project_services", baseTableSchemaName: "dbo", constraintName: "FKB5A62C4E80565C9")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-33") {
		dropIndex(indexName: "UK_principal_name", schemaName: "dbo", tableName: "sysdiagrams")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-34") {
		dropTable(schemaName: "dbo", tableName: "project_services")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-35") {
		dropTable(schemaName: "dbo", tableName: "sysdiagrams")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-16") {
		addForeignKeyConstraint(baseColumnNames: "acc_executive_id", baseTableName: "project", constraintName: "FKED904B194BD6882F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "employee", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-17") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "project", constraintName: "FKED904B19F3E016AB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-18") {
		addForeignKeyConstraint(baseColumnNames: "manager_id", baseTableName: "project", constraintName: "FKED904B191539A74C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "employee", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-19") {
		addForeignKeyConstraint(baseColumnNames: "parent_project_id", baseTableName: "project", constraintName: "FKED904B196D2A79FE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "project", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-20") {
		addForeignKeyConstraint(baseColumnNames: "project_category_id", baseTableName: "project", constraintName: "FKED904B19D66D883C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "project_category", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-21") {
		addForeignKeyConstraint(baseColumnNames: "project_lead_id", baseTableName: "project", constraintName: "FKED904B19F4D50E17", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "employee", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-22") {
		addForeignKeyConstraint(baseColumnNames: "planned_labor_id", baseTableName: "project_detail", constraintName: "FK1F6CCC37AF78F56D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "project_labor", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-23") {
		addForeignKeyConstraint(baseColumnNames: "project_id", baseTableName: "project_detail", constraintName: "FK1F6CCC3750B45B49", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "project", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-24") {
		addForeignKeyConstraint(baseColumnNames: "project_product_id", baseTableName: "project_detail", constraintName: "FK1F6CCC37D5C687D8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "product", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-25") {
		addForeignKeyConstraint(baseColumnNames: "service_id", baseTableName: "project_detail", constraintName: "FK1F6CCC37E80565C9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "product", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-26") {
		addForeignKeyConstraint(baseColumnNames: "expense_code_id", baseTableName: "project_expense_detail", constraintName: "FK41F727FE3B6A2C4D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "product", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-27") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "project_labor", constraintName: "FKC7A3B78AB5170BE8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "appuser", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-28") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "project_labor", constraintName: "FKC7A3B78AD605837B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "appuser", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-29") {
		addForeignKeyConstraint(baseColumnNames: "created_by_id", baseTableName: "project_labor_detail", constraintName: "FK334CAEA6B5170BE8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "appuser", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-30") {
		addForeignKeyConstraint(baseColumnNames: "labor_activity_code_id", baseTableName: "project_labor_detail", constraintName: "FK334CAEA65D120307", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "labor_activity_code", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-31") {
		addForeignKeyConstraint(baseColumnNames: "project_labor_id", baseTableName: "project_labor_detail", constraintName: "FK334CAEA640413BB8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "project_labor", referencesUniqueColumn: "false")
	}

	changeSet(author: "tbthakker (generated)", id: "1438241534123-32") {
		addForeignKeyConstraint(baseColumnNames: "updated_by_id", baseTableName: "project_labor_detail", constraintName: "FK334CAEA6D605837B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "appuser", referencesUniqueColumn: "false")
	}
}
