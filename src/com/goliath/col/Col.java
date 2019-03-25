package com.goliath.col;

public class Col
{

	private final String name;
	private final StringBuilder column;

	public Col(String name, Type type)
	{
		this.name = name;
		column = new StringBuilder();
		if (type != Type.NONE)
			column.append(name + " " + type.toString().replaceAll("_", " "));
		else
			column.append(name);
	}

	public String getName()
	{
		return name;
	}

	public Col primaryKey()
	{
		column.append(" PRIMARY KEY");
		return this;
	}

	public Col foreignKey()
	{
		column.append(" FOREIGN KEY");
		return this;
	}

	public Col notNull()
	{
		column.append(" NOT NULL");
		return this;
	}
	
	public Col check(String conditions)
	{
		column.append(" CHECK(" + conditions + ")");
		return this;
	}
	
	public Col def(String val)
	{
		column.append(" DEFAULT " + val);
		return this;
	}
	
	public Col cus(String val)
	{
		column.append(" " + val);
		return this;
	}

	public String push()
	{
		return column.toString();
	}
}
