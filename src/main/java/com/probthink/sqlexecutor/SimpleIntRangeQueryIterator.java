package com.probthink.sqlexecutor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.probthink.sqlexecutor.IQueryIterator;


public class SimpleIntRangeQueryIterator implements IQueryIterator {

	@Override
	public String getSQL() {
		return sql;
	}

	@Override
	public void prepare(PreparedStatement ps) throws SQLException {
		int nextId = currentId + interval;
		if (nextId > endId)
			nextId = endId;
		System.out.println("Current Id & Next ID: " + currentId + ", " + nextId);
		
		ps.setInt(1, currentId);
		ps.setInt(2, nextId);
	}

	@Override
	public boolean next() {
		if (!hasCurrentId){
			currentId = startId - interval; hasCurrentId = true;
		}
		if (currentId + interval > endId) {
			return false;
		}
		currentId += interval;
		return true;
	}

	public void reset() {
		currentId = startId - 1;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setEndId(int endId) {
		this.endId = endId;
	}

	public void setStartId(int startId) {
		this.startId = startId;
	}

	public String getSql() {
		return sql;
	}

	public int getInterval() {
		return interval;
	}

	public int getEndId() {
		return endId;
	}

	public int getStartId() {
		return startId;
	}

	private String sql;
	private int currentId;
	private int interval;
	private int endId;
	private int startId;
	private boolean hasCurrentId = false;

}