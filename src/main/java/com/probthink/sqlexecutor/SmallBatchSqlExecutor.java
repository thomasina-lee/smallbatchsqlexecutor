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
import org.yaml.snakeyaml.Yaml;



import com.probthink.sqlexecutor.CsvRecordSetProcessor;
import com.probthink.sqlexecutor.DbDriver;
import com.probthink.sqlexecutor.LoopQueryExecutor;
import com.probthink.sqlexecutor.SimpleIntRangeQueryIterator;
import com.probthink.sqlexecutor.SmallBatchSqlExecutor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;




public class SmallBatchSqlExecutor {



	
	private static SmallBatchSqlExecutor loadXMLConfig() throws FileNotFoundException{
		XStream xs ;
		switch (inputType){
		case 1:
			xs = new XStream(new StaxDriver());
			break;
		default:
			xs = new XStream(new JettisonMappedXmlDriver());
			break;
		}
		

		xs.alias("main", SmallBatchSqlExecutor.class);
		xs.alias("connection", LoopQueryExecutor.class);
		xs.alias("csv", CsvRecordSetProcessor.class);
		xs.alias("intsql", SimpleIntRangeQueryIterator.class);
		xs.alias("driver", DbDriver.class);
		SmallBatchSqlExecutor br = (SmallBatchSqlExecutor) xs
				.fromXML(new FileInputStream(filename));
		return br;
	}
	
	
	private static SmallBatchSqlExecutor loadYamlConfig() throws FileNotFoundException{
		Yaml yaml = new Yaml() ;
		
		SmallBatchSqlExecutor br = (SmallBatchSqlExecutor) yaml.load(new FileInputStream(filename));
		
		return br;
	}
	
	private static void run(SmallBatchSqlExecutor br) throws Exception {
		
		br.getDbDriver().loadDriver();
		System.out.println(br.getExecutor().getSQL());
		
		long start = System.currentTimeMillis();
		br.getExecutor().run();
		System.out.println("RunTime: " + (System.currentTimeMillis() - start)
				+ " millisecs");
		
		//XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		//System.out.print(xstream.toXML(br));

		
	}
	


	private static void printUsageAndExit(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("<this> ", options);
		Runtime.getRuntime().exit(-1);
	}

	private static void parseCommandLine(String[] args)	 {

		//String filename = "";
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		options.addOption("h", "help", false, "print this message.");
		options.addOption("x", "xml-file", true,
				"set the xml config file to use");
		options.addOption("j", "json-file", true,
				"set the json config file to use");
		options.addOption("y", "yaml-file", true,
		"set the yaml config file to use");

		
		
		CommandLine cmdline;
		try {
			cmdline = parser.parse(options, args);
			
			if (cmdline.hasOption("h")) {
				printUsageAndExit(options);
			}

			if (cmdline.hasOption("x")) {
				filename = cmdline.getOptionValue("xml-file");
				inputType = 0;
			} else if (cmdline.hasOption("j")) {
				filename = cmdline.getOptionValue("json-file");
				inputType = 1;
			} else if (cmdline.hasOption("y")) {
				filename = cmdline.getOptionValue("yaml-file");
				inputType = 2;
			} else {
				printUsageAndExit(options);
			}
			
			
		} catch (ParseException e) {
			
			printUsageAndExit(options);
		}

		


	}

	public static void main(String arg[]) {
		SmallBatchSqlExecutor br;
		try {
			
			parseCommandLine(arg);
			switch (inputType){
			case 2:
				br = loadYamlConfig();
				break;
			default:
				br = loadXMLConfig();
				break;
			}
			
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
	static int inputType = 0;
	
}
