package org.kpi.campus.convert.parser;

import java.util.ArrayList;

/**
 * @author Anton Bidkov
 *
 */
public class Convert implements Cloneable {
	private String tableNameFrom;
	private String tableNameTo;
	private ArrayList<Column> columnsFrom;
	private ArrayList<Column> columnsTo;

	public Convert(String tableNameFrom, String tableNameTo) {
		this.tableNameFrom = tableNameFrom;
		this.tableNameTo = tableNameTo;
		this.columnsFrom = new ArrayList<Column>();
		this.columnsTo = new ArrayList<Column>();
	}
	public String getTableNameFrom() {
		return tableNameFrom;
	}
	public String getTableNameTo() {
		return tableNameTo;
	}
	public void addColumns(Column from, Column to) {
		columnsFrom.add(from);
		columnsTo.add(to);
	}
	public int getColumnsNumber() {
		return columnsFrom.size(); 
	}
	public Column getColumnFrom(int index) {
		return columnsFrom.get(index);
	}
	public Column getColumnTo(int index) {
		return columnsTo.get(index);
	}
}
