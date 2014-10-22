package com.probthink.sqlexecutor;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

class DriverShim implements Driver {
	private Driver driver;

	DriverShim(Driver d) {
		this.driver = d;
	}

	public boolean acceptsURL(String u) throws SQLException {
		return this.driver.acceptsURL(u);
	}

	public Connection connect(String u, Properties p) throws SQLException {
		return this.driver.connect(u, p);
	}

	public int getMajorVersion() {
		return this.driver.getMajorVersion();
	}

	public int getMinorVersion() {
		return this.driver.getMinorVersion();
	}

	public DriverPropertyInfo[] getPropertyInfo(String u, Properties p)
			throws SQLException {
		return this.driver.getPropertyInfo(u, p);
	}

	public boolean jdbcCompliant() {
		return this.driver.jdbcCompliant();
	}
}

public class DbDriver {

	public void loadDriver() throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		

			URL classUrl = new URL(driverUrl);
			URL[] classUrls = { classUrl };
			URLClassLoader ucl = new URLClassLoader(classUrls);

			Driver d = (Driver) Class.forName(driverClassName, true, ucl)
					.newInstance();
			DriverManager.registerDriver(new DriverShim(d));

		

	};

	public String getDriverUrl() {
		return driverUrl;
	}

	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;

	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;

	}

	String driverUrl;
	String driverClassName;

}