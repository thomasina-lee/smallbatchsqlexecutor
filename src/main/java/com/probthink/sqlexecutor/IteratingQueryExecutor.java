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


public class IteratingQueryExecutor implements IQueryExecutor {

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


		resultSetProcess.preProcess();
		while (queryIterator.next()) {
			PreparedStatement ps = con.prepareStatement(queryIterator.getSQL(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
					Connection.TRANSACTION_READ_UNCOMMITTED);
			queryIterator.prepare(ps);
			ResultSet rs = ps.executeQuery();
			resultSetProcess.process(rs);
			rs.close();
			ps.close();
		}
		resultSetProcess.postProcess();
		

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

	public IQueryIterator getQueryIterator() {
		return queryIterator;
	}

	public void setQueryIterator(IQueryIterator queryIterator) {
		this.queryIterator = queryIterator;
	}

	public IResultSetProcess getResultSetProcess() {
		return resultSetProcess;
	}

	public void setResultSetProcess(IResultSetProcess resultSetProcess) {
		this.resultSetProcess = resultSetProcess;
	}
	
	public String getSQL() {
		
		return queryIterator.getSQL();
	}

	private String dbConnStr;
	private String dbUserName;
	private String dbPassword;
	
	private ArrayList<String> stagingSqls;

	private IQueryIterator queryIterator;
	private IResultSetProcess resultSetProcess;


}