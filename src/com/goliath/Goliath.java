package com.goliath;

import com.goliath.col.Col;
import com.goliath.col.Type;
import com.goliath.db.Database;
import com.goliath.table.Table;

public class Goliath
{
	private static Database db;
	private static Table ACCOUNTS;

	public static void main(String[] args)
	{
		db = SQLWrapper.getInstance().createDatabase("data/test.db");

		SQLWrapper.getInstance().forDatabase(db).createTable(ACCOUNTS = new Table("ACCOUNTS", new String[] {
				new Col("ID", Type.INT).primaryKey().notNull().push(),
				new Col("BALANCE", Type.REAL).push()
		}))
		
				.insertOrIgnore(ACCOUNTS, "9283712742, 0")

				.addColumn(ACCOUNTS, new Col("KEY", Type.REAL))

				.updateEntry(ACCOUNTS, "BALANCE = 69", "ID = 9283712742")
				
				.filterToNewTable("New_accts", ACCOUNTS.getName(), "")
				
				.removeEntry(ACCOUNTS, "ID = 9283712742");

		db.flush();
	}
}
