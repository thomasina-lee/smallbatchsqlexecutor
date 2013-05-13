package com.probthink.sqlexecutor;


import java.sql.SQLException;

public interface IQueryExecutor {
	public String getSQL();

	public void run() throws ClassNotFoundException, SQLException, Exception;

	
}