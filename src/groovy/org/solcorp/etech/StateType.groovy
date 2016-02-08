package org.solcorp.etech

public enum  StateType {
		
	ALABAMA(1, "Alabama"),
	ALASKA(2, "Alaska"),
	ARIZONA(3, "Arizona"),
	ARKANSAS(4, "Arkansas"),
	CALIFORNIA(5, "California"),
	COLORADO(6, "Colorado"),
	CONNECTICUT(7, "Connecticut"),
	DELAWARE(8, "Delaware"),
	FLORIDA(9, "Florida"),
	GEORGIA(10, "Georgia"),
	HAWAII(11, "Hawaii"),
	IDAHO(12, "Idaho"),
	ILLINOIS(13, "Illinois"),
	INDIANA(14, "Indiana"),
	IOWA(15, "Iowa"),
	KANSAS(16, "Kansas"),
	KENTUCKY(17, "Kentucky"),
	LOUISIANA(18, "Louisiana"),
	MAINE(19, "Maine"),
	MARYLAND(20, "Maryland"),
	MASSACHUSETTS(21, "Massachusetts"),
	MICHIGAN(22, "Michigan"),
	MINNESOTA(23, "Minnesota"),
	MISSISSIPPI(24, "Mississippi"),
	MISSOURI(25, "Missouri"),
	MONTANA(26, "Montana"),
	NEBRASKA(27, "Nebraska"),
	NEVADA(28, "Nevada"),
	NEW_HAMPSHIRE(29, "New Hampshire"),
	NEW_JERSEY(30, "New Jersey"),
	NEW_MEXICO(31, "New Mexico"),
	NEW_YORK(32, "New York"),
	NORTH_CAROLINA(33, "North Carolina"),
	NORTH_DAKOTA(34, "North Dakota"),
	OHIO(35, "Ohio"),
	OKLAHOMA(36, "Oklahoma"),
	OREGON(37, "Oregon"),
	PENNSYLVANIA(38, "Pennsylvania"),
	RHODE_ISLAND(39, "Rhode Island"),
	SOUTH_CAROLINA(40, "South Carolina"),
	SOUTH_DAKOTA(41, "South Dakota"),
	TENNESSEE(42, "Tennessee"),
	TEXAS(43, "Texas"),
	UTAH(44, "Utah"),
	VERMONT(45, "Vermont"),
	VIRGINIA(46, "Virginia"),
	WASHINGTON(47, "Washington"),
	WEST_VIRGINIA(48, "West Virginia"),
	WISCONSIN(49, "Wisconsin"),
	WYOMING(50, "Wyoming")
	
	private final String value
	private final String id
	
	public StateType(Integer id,String value) {
		this.id = id
		this.value = value
	}
	
	public int getKey() {
		return id
	}
	
	public String getValue() {
		return value
	}
	
	static stateList() {
		return [ALABAMA, ALASKA, ARIZONA, ARKANSAS, CALIFORNIA, COLORADO, CONNECTICUT, DELAWARE, FLORIDA, GEORGIA, HAWAII, IDAHO, ILLINOIS, INDIANA, IOWA, KANSAS, KENTUCKY, LOUISIANA, MAINE, MARYLAND, MASSACHUSETTS, MICHIGAN, MINNESOTA, MISSISSIPPI, MISSOURI, MONTANA, NEBRASKA, NEVADA, NEW_HAMPSHIRE, NEW_JERSEY, NEW_MEXICO, NEW_YORK, NORTH_CAROLINA, NORTH_DAKOTA, OHIO, OKLAHOMA, OREGON, PENNSYLVANIA, RHODE_ISLAND, SOUTH_CAROLINA, SOUTH_DAKOTA, TENNESSEE, TEXAS, UTAH, VERMONT, VIRGINIA, WASHINGTON, WEST_VIRGINIA, WISCONSIN, WYOMING]
	}
	
	public String toString() {
		return this.getValue()
	}
}
