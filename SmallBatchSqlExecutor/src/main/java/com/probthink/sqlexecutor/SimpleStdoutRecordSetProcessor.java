package com.probthink.sqlexecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.probthink.sqlexecutor.IResultSetProcess;


public class SimpleStdoutRecordSetProcessor implements IResultSetProcess {

	@Override
	public void preProcess() throws SQLException, Exception {

	}

	@Override
	public void process(ResultSet rs) throws SQLException, Exception {
		try {

			while (rs.next()) {
				for (int ii = 0; ii < rs.getMetaData().getColumnCount(); ii++) {
					if (ii > 0) System.out.print("\t");
					
					System.out.print(rs.getObject(ii + 1).toString());
					
				}
				System.out.print("\n");
			}


		} finally {
			
		}
	}

	@Override
	public void postProcess() throws SQLException, Exception {
		
	}

}
