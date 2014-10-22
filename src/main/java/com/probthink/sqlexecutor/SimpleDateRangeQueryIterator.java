package com.probthink.sqlexecutor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.probthink.sqlexecutor.IQueryIterator;

// we use epoch :)
public class SimpleDateRangeQueryIterator implements IQueryIterator {

	@Override
	public String getSQL() {
		return sql;
	}

	@Override
	public void prepare(PreparedStatement ps) throws SQLException {		
		
		if (!hasCurrentDate) throw new SQLException("Date input configuration is incorrect");
		long nextId = currentId + interval ;
		if (nextId > endId)
			nextId = endId;
		System.out.println("Current Id & Next ID: " + currentId + ", " + nextId);
		
		ps.setDate(1, new java.sql.Date(currentId ));
		ps.setDate(2, new java.sql.Date(nextId ));
		
	}

	@Override
	public boolean next() {
		if (!hasCurrentDate){
			reset();
		}
		if (currentId + interval  > endId) {
			return false;
		}
		currentId += interval ;
		return true;
	}

	public void reset()  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			currentId = sdf.parse(startDate).getTime() - interval;
			endId = sdf.parse(endDate).getTime() ;
			hasCurrentDate = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setDateInterval(int dateInterval) {
		this.interval = dateInterval * EPOAH_DATE;
		this.dateInterval = dateInterval;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSql() {
		return sql;
	}





	private String sql;

	private boolean hasCurrentDate = false;
	
	private long currentId;
	private long endId;
	
	private static long EPOAH_DATE = 86400000L;
	private int dateInterval;
	private long interval;
	private String endDate;
	private String startDate;

	

}