package com.goliath.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
	private final Connection connection;
	private final String path;

	private final StringBuilder statementBuilder;

	public Database(Connection connection, String path)
	{
		this.connection = connection;
		this.path = path;
		this.statementBuilder = new StringBuilder();
	}

	public Connection getConnection()
	{
		return connection;
	}

	public String getPath()
	{
		return path;
	}

	public StringBuilder getStatementBuilder()
	{
		return statementBuilder;
	}

	public void execute()
	{
		try (Statement statement = connection.createStatement())
		{
			statement.executeUpdate(statementBuilder.toString());
			connection.commit();
		}
		catch (SQLException e)
		{
			System.out.println(statementBuilder.toString());
			e.printStackTrace();
		}
		statementBuilder.setLength(0);
	}

	public void flush()
	{
		try
		{
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
