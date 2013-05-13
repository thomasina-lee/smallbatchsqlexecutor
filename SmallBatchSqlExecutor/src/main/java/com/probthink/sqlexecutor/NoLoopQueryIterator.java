package com.probthink.sqlexecutor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.probthink.sqlexecutor.IQueryIterator;


public class NoLoopQueryIterator implements IQueryIterator {

	@Override
	public String getSQL() {
		return sql;
	}

	@Override
	public void prepare(PreparedStatement ps) throws SQLException {
		// do nothing this is not suppose to loop
	}

	@Override
	public boolean next() {
		if (nextCount == 0) {
			nextCount++;
			return true;
		}
		return false;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	private String sql;
	private int nextCount = 0;

}
