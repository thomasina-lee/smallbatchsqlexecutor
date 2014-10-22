package com.probthink.sqlexecutor;

import java.io.BufferedReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.probthink.sqlexecutor.IResultSetProcess;


public class SimpleStdoutClobFilterRecordSetProcessor implements IResultSetProcess {

	@Override
	public void preProcess() throws SQLException, Exception {
	}

	@Override
	public void process(ResultSet rs) throws SQLException, Exception {
		try {

			while (rs.next()) {
			    BufferedReader bufferRead = new BufferedReader(rs.getClob(clobColumn).getCharacterStream());
			    String strng;
			    StringBuffer sb = new StringBuffer();
			    while ((strng=bufferRead.readLine())!=null)
			       sb.append(strng);
			    String str = sb.toString();
			    
			    if (str.matches(regex)){

			    	for (int ii = 0; ii < rs.getMetaData().getColumnCount(); ii++) {
			    		if (ii > 0) System.out.print("\t");
			    		int coltype = rs.getMetaData().getColumnType(ii);
			    		switch (coltype){
			    		case java.sql.Types.CLOB:
			    		case java.sql.Types.BLOB:
			    			break;
			    		default:
			    			System.out.print(rs.getObject(ii + 1).toString());
			    		}
					
			    	}
			    }
				System.out.print("\n");
			}


		} finally {
			
		}
	}

	@Override
	public void postProcess() throws SQLException, Exception {
		
	}

	public void setClobColumn(String clobColumn) {
		this.clobColumn = clobColumn;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	private String regex;
	private String clobColumn;
	
	
}
