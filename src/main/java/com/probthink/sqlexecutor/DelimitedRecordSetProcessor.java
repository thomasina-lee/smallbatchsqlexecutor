package com.probthink.sqlexecutor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.probthink.sqlexecutor.IResultSetProcess;


public class DelimitedRecordSetProcessor implements IResultSetProcess {

	@Override
	public void preProcess() throws IOException {
		Writer output = new BufferedWriter(new FileWriter(filename));
		output.close();
		firstCall = true;
	}

	@Override
	public void process(ResultSet rs) throws SQLException, IOException {

		Writer output = new BufferedWriter(new FileWriter(filename, true));
		StringBuffer sb = new StringBuffer();
		try {
			if (firstCall){
				if (header){
					for (int ii = 0; ii < rs.getMetaData().getColumnCount(); ii++) {
						if (ii > 0)
							sb.append(delimiter);
						sb.append(quote);
						sb.append(rs.getMetaData().getColumnName(ii+1));
						sb.append(quote);
					}
					sb.append(lineDelimiter);
				}
				firstCall = false;
			}
			
			while (rs.next()) {
				for (int ii = 0; ii < rs.getMetaData().getColumnCount(); ii++) {
					if (ii > 0)
						sb.append(delimiter);
					sb.append(quote);
					sb.append(rs.getObject(ii + 1) != null ? rs.getObject(ii + 1).toString() : nullSymbol);
					sb.append(quote);
				}
				sb.append(lineDelimiter);
			}

			output.write(sb.toString());

		} finally {
			output.close();
		}

		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}

	@Override
	public void postProcess() throws Exception {

	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getLineDelimiter() {
		return lineDelimiter;
	}

	public void setLineDelimiter(String lineDelimiter) {
		this.lineDelimiter = lineDelimiter;
	}

	public boolean isHeader() {
		return header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}
	
	private boolean firstCall = false;
	
	private String filename;
	private String delimiter = ",";
	private String quote = "";
	private String lineDelimiter = "\n";
	private String nullSymbol = "";
	private boolean header = true;

}