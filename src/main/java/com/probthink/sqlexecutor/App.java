package com.probthink.sqlexecutor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;


import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.probthink.sqlexecutor.DelimitedRecordSetProcessor;
import com.probthink.sqlexecutor.DbDriver;
import com.probthink.sqlexecutor.IteratingQueryExecutor;
import com.probthink.sqlexecutor.SimpleIntRangeQueryIterator;
import com.probthink.sqlexecutor.App;


public class App {



	
	
	private static App loadYamlConfig()
			throws FileNotFoundException {
		
		Constructor constructor = new Constructor(App.class);//Car.class is root

		constructor.addTypeDescription(new TypeDescription(App.class, "!app"));
		constructor.addTypeDescription(new TypeDescription(IteratingQueryExecutor.class, "!iterating_executor"));
		constructor.addTypeDescription(new TypeDescription(SimpleIntRangeQueryIterator.class, "!int_iterator"));
		constructor.addTypeDescription(new TypeDescription(SimpleDateRangeQueryIterator.class, "!date_iterator"));
		constructor.addTypeDescription(new TypeDescription(SimpleQueryExecutor.class, "!simple_executor"));
		
		
		constructor.addTypeDescription(new TypeDescription(DelimitedRecordSetProcessor.class, "!delimited_processor"));
		
		
		Yaml yaml = new Yaml(constructor);
		
		

		App br = (App) yaml
				.load(new FileInputStream(filename));

		return br;
	}

	private static void run(App br) throws Exception {

		br.getDbDriver().loadDriver();
		System.out.println(br.getExecutor().getSQL());

		long start = System.currentTimeMillis();
		br.getExecutor().run();
		System.out.println("RunTime: " + (System.currentTimeMillis() - start)
				+ " millisecs");

		// XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		// System.out.print(xstream.toXML(br));

	}

	private static void printUsageAndExit(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(" ", options);
		Runtime.getRuntime().exit(-1);
	}

	private static void parseCommandLine(String[] args) {

		// String filename = "";
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		options.addOption("h", "help", false, "print this message.");
		options.addOption("y", "yaml-file", true, "specify yaml config file to run.");



		
		
		
		CommandLine cmdline;
		try {
			cmdline = parser.parse(options, args);

			if (cmdline.hasOption("h")) {
				printUsageAndExit(options);
			}

			if (cmdline.hasOption("yaml-file")) {
				filename = cmdline.getOptionValue("yaml-file");
			
			} else {
				printUsageAndExit(options);
			}

		} catch (ParseException e) {

			printUsageAndExit(options);
		}

	}

	public static void main(String arg[]) {
		App br;
		try {

			parseCommandLine(arg);
			br = loadYamlConfig();

			run(br);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL exception Error Code: " + e.getErrorCode());
			System.err.println("SQL exception SQL State:  " + e.getSQLState());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DbDriver getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(DbDriver dbDriver) {
		this.dbDriver = dbDriver;
	}

	public IQueryExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(IQueryExecutor executor) {
		this.executor = executor;
	}

	DbDriver dbDriver;
	IQueryExecutor executor;

	static String filename = "";
	

}
