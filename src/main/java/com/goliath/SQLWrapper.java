package com.goliath;

import java.sql.Connection;
import java.sql.DriverManager;

import com.goliath.col.Col;
import com.goliath.db.Database;
import com.goliath.table.Table;

public class SQLWrapper
{
	private Database db;

	/**
	 * @return A new {@link SQLWrapper} instance to work with.
	 */
	public static SQLWrapper getInstance()
	{
		return new SQLWrapper();
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public Database createDatabase(String path)
	{
		Connection c = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + path);
			c.setAutoCommit(false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new Database(c, path);
	}

	/**
	 * 
	 * @param db
	 * @return
	 */
	public SQLWrapper forDatabase(Database db)
	{
		this.db = db;
		return this;
	}

	/**
	 * 
	 * @param table
	 * @return
	 */
	public SQLWrapper createTable(Table table)
	{
		db.getStatementBuilder().append("CREATE TABLE IF NOT EXISTS " + table.getName() + "(");

		for (int i = 0; i < table.getColumns().size() - 1; i++)
		{
			db.getStatementBuilder().append(table.getColumns().get(i) + ", ");
		}

		db.getStatementBuilder().append(table.getColumns().get(table.getColumns().size() - 1) + ");");
		return this.push();
	}

	/**
	 * 
	 * @param newTableName
	 * @param oldTableName
	 * @param conditions
	 * @return
	 */
	public SQLWrapper filterToNewTable(String newTableName, String oldTableName, String conditions)
	{
		if(!conditions.equals(""))
			db.getStatementBuilder().append("CREATE TABLE IF NOT EXISTS " + newTableName + " AS SELECT * FROM " + oldTableName + " WHERE " + conditions + ";");
		else
			db.getStatementBuilder().append("CREATE TABLE IF NOT EXISTS " + newTableName + " AS SELECT * FROM " + oldTableName + ";");
		return this.push();
	}

	/**
	 * 
	 * @param table
	 * @return
	 */
	public SQLWrapper deleteTable(Table table)
	{
		db.getStatementBuilder().append("DROP TABLE " + table.getName());
		return this.push();
	}

	/**
	 * 
	 * @param table
	 * @param changes
	 * @return
	 */
	public SQLWrapper insertOrIgnore(Table table, String changes)
	{
		db.getStatementBuilder().append("INSERT OR IGNORE INTO " + table.getName() + "(");

		for (int i = 0; i < table.getColumns().size() - 1; i++)
		{
			db.getStatementBuilder().append(table.getColumnNames().get(i) + ", ");
		}

		db.getStatementBuilder().append(table.getColumnNames().get(table.getColumns().size() - 1) + ") ");

		db.getStatementBuilder().append("VALUES(" + changes + ");");
		return this.push();
	}

	/**
	 * 
	 * @param table
	 * @param changes
	 * @param specifier
	 * @return
	 */
	public SQLWrapper updateEntry(Table table, String changes, String specifier)
	{
		db.getStatementBuilder().append("UPDATE " + table.getName() + " SET " + changes + " WHERE " + specifier + ";");
		System.out.println(db.getStatementBuilder().toString());
		return this.push();
	}

	/**
	 * 
	 * @param table
	 * @param changes
	 * @return
	 */
	public SQLWrapper updateAll(Table table, String changes)
	{
		db.getStatementBuilder().append("UPDATE " + table.getName() + " SET " + changes + ";");
		System.out.println(db.getStatementBuilder().toString());
		return this.push();
	}

	/**
	 * 
	 * @param table
	 * @param column
	 * @return
	 */
	public SQLWrapper addColumn(Table table, String column)
	{
		db.getStatementBuilder().append("ALTER TABLE " + table.getName() + " ADD " + column);
		table.getColumnNames().add(column.split(" ")[0]);
		return this.push();
	}
	
	/**
	 * 
	 * @param table
	 * @param column
	 * @return
	 */
	public SQLWrapper addColumn(Table table, Col column)
	{
		db.getStatementBuilder().append("ALTER TABLE " + table.getName() + " ADD " + column.push());
		table.getColumnNames().add(column.push().split(" ")[0]);
		return this.push();
	}
	
	/**
	 * 
	 * @param table
	 * @param conditions
	 * @return
	 */
	public SQLWrapper removeEntry(Table table, String conditions)
	{
		db.getStatementBuilder().append("DELETE FROM " + table.getName() + " WHERE " + conditions + ";");
		return this.push();
	}

	private SQLWrapper push()
	{
		db.execute();
		return this;
	}
}