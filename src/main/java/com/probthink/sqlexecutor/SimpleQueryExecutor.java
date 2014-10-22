package com.probthink.sqlexecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.probthink.sqlexecutor.IQueryIterator;
import com.probthink.sqlexecutor.IResultSetProcess;


public class SimpleQueryExecutor implements IQueryExecutor {

	private Connection getConnection() throws ClassNotFoundException,
			SQLException {

		return DriverManager.getConnection(dbConnStr, dbUserName, dbPassword);

	}

	public void run() throws ClassNotFoundException, SQLException, Exception {
		Connection con = getConnection();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		con.setAutoCommit(false);
		Statement st = con.createStatement();
		if (stagingSqls != null){
			for(String stagingSql : stagingSqls ){
				st.execute(stagingSql);
			}
		}
		st.close();

		st = con.createStatement();
		resultSetProcess.preProcess();
		
		ResultSet rs = st.executeQuery(this.getSql());
		resultSetProcess.process(rs);
		rs.close();
		st.close();
		
		resultSetProcess.postProcess();
		

	}
	


	@Override
	public String getSQL() {
		return getSql();
	}
	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String sql){
		this.sql = sql;  
	}

	public String getDbConnStr() {
		return dbConnStr;
	}

	public void setDbConnStr(String dbConnStr) {
		this.dbConnStr = dbConnStr;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public ArrayList<String> getStagingSqls() {
		return stagingSqls;
	}

	public void setStagingSqls(ArrayList<String> stagingSqls) {
		this.stagingSqls = stagingSqls;
	}


	public IResultSetProcess getResultSetProcess() {
		return resultSetProcess;
	}

	public void setResultSetProcess(IResultSetProcess resultSetProcess) {
		this.resultSetProcess = resultSetProcess;
	}

	private String dbConnStr;
	private String dbUserName;
	private String dbPassword;
	
	private ArrayList<String> stagingSqls;

	private String sql;
	private IResultSetProcess resultSetProcess;




}