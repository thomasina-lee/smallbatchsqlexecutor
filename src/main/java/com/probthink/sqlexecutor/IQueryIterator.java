package com.probthink.sqlexecutor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IQueryIterator {
	public String getSQL();

	public void prepare(PreparedStatement ps) throws SQLException;

	public boolean next();
}