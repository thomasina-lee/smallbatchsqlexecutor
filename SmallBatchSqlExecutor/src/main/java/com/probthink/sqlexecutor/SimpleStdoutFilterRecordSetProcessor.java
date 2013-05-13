package com.probthink.sqlexecutor;

import java.io.BufferedReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.probthink.sqlexecutor.IResultSetProcess;


public class SimpleStdoutFilterRecordSetProcessor implements IResultSetProcess {

	@Override
	public void preProcess() throws SQLException, Exception {
		pattern = Pattern.compile(regex, Pattern.MULTILINE);
		
	}

	@Override
	public void process(ResultSet rs) throws SQLException, Exception {
		try {
			int count = 0;

			while (rs.next()) {
				count++;
			    if ((count % 1000) == 0) System.out.println("count:" + count);


			    if ( pattern.matcher(rs.getString(stringColumn)).find() ){

			    	for (int ii = 0; ii < rs.getMetaData().getColumnCount(); ii++) {
			    		if (ii > 0) System.out.print("\t");
			    		int coltype = rs.getMetaData().getColumnType(ii+ 1);
			    		switch (coltype){
			    		case java.sql.Types.CLOB:
			    		case java.sql.Types.BLOB:
			    			break;
			    		default:
			    			System.out.print(rs.getObject(ii + 1).toString());
			    		}
					
			    	}
			    	System.out.print("\n");
			    }
				
			}


		} finally {
			
		}
	}

	@Override
	public void postProcess() throws SQLException, Exception {
		
	}

	public void setStringColumn(String stringColumn) {
		this.stringColumn = stringColumn;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	private Pattern pattern;
	private String regex;
	private String stringColumn;
	
	
}
