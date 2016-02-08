package org.solcorp.etech

public enum PredefinedReport {
		
	YESTERDAY(1, "Yesterday"),
	TODAY(2, "Today"),
	CURRENT_WEEK(3, "Current Week"),
	CURRENT_MONTH(4, "Current Month"),
	CURRENT_QTR(5, "Current Quarter"),
	CURRENT_YEAR(6, "Current Year"),
	LAST_WEEK(7, "Last Week"),
	LAST_MONTH(8, "Last Month"),
	LAST_QTR(9, "Last Quarter"),
	LAST_YEAR(10, "Last Year")
			
	private final String value
	private final String id
	
	public PredefinedReport(Integer id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public int getKey() {
		return id
	}
	
	public String getValue() {
		return value
	}
	
	static list() {
		return [YESTERDAY, TODAY, CURRENT_WEEK, CURRENT_MONTH, CURRENT_QTR, CURRENT_YEAR, LAST_WEEK, LAST_MONTH, LAST_QTR, LAST_YEAR]
	}
	 	
	public String toString() { 
		return this.getValue() 
	}
}
