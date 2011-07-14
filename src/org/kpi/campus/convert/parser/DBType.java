package org.kpi.campus.convert.parser;

/**
 * Enumerates supported database types.
 * 
 * @author Anton Bidkov
 *
 */
public enum DBType {
	MYSQL("MYSQL"), MSSQL("MSSQL");

	private final String abbreviation;

	private DBType(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
	
}
