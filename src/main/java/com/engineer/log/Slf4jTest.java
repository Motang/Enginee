package com.engineer.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("log4j.configuration", "log4j_test.properties");
		Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
		
		logger.info("{}...", "testing...");
	}

}
