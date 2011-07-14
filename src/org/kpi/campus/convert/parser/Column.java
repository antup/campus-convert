package org.kpi.campus.convert.parser;

/**
 * Class represents column.
 * @author Anton Bidkov
 *
 */
public class Column {
	private String name;
	private String modifier;
	private String type;
	
	public Column (String name, String modifier, String type) {
		this.name = name;
		this.modifier = modifier;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getModifier() {
		return modifier;
	}

	public String getType() {
		return type;
	}

}
