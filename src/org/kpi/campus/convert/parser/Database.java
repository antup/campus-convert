package org.kpi.campus.convert.parser;

import java.util.ArrayList;

/**
 * @author Anton Bidkov
 *
 */
public class Database {
	private String csFrom;
	private DBType dbTypeFrom;
	private String csTo;
	private DBType dbTypeTo;
	private ArrayList<Convert> converts;

	public Database(String csFrom, DBType dbTypeFrom, String csTo, DBType dbTypeTo) {
		this.csFrom = csFrom;
		this.dbTypeFrom = dbTypeFrom;
		this.csTo = csTo;
		this.dbTypeTo = dbTypeTo;
		converts = new ArrayList<Convert>();
	}
	public String getCsFrom() {
		return csFrom;
	}
	public DBType getDbTypeFrom() {
		return dbTypeFrom;
	}
	public String getCsTo() {
		return csTo;
	}
	public DBType getDbTypeTo() {
		return dbTypeTo;
	}
	public void addConvert(Convert convert) {
		converts.add(convert);
	}
	public int getConvertsNumber() {
		return converts.size();
	}
	public Convert getConvert(int index) {
		return converts.get(index);
	}

}
