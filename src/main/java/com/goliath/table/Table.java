package com.goliath.table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Table
{
	private final String name;
	private List<String> columns;
	private List<String> columnNames;
	
	public Table(String name, String... columns)
	{
		this.name = name;
		this.columns = Arrays.asList(columns);
		this.columnNames = Arrays.stream(columns).map(column -> column.split(" ")[0]).collect(Collectors.toList());
	}

	public String getName()
	{
		return name;
	}

	public List<String> getColumns()
	{
		return columns;
	}

	public List<String> getColumnNames()
	{
		return columnNames;
	}
}
