package com.probthink.sqlexecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IResultSetProcess {
	public void preProcess() throws Exception;

	public void process(ResultSet rs) throws SQLException, Exception;

	public void postProcess() throws Exception;
}