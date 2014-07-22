package com.engineer.log;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LogResearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		for (StackTraceElement element : stackTrace) {
			System.out.println(element.getClassName()   
                    + "\t" + element.getFileName()   
                    + "\t" + element.getLineNumber()  
                    + "\t" + element.getMethodName()); 
		}
		
		Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
		Set<Entry<Thread, StackTraceElement[]>> entrySet = allStackTraces.entrySet();
		for (Entry<Thread, StackTraceElement[]> entry : entrySet) {
			Thread key = entry.getKey();
			StackTraceElement[] values = entry.getValue();
			
			for (StackTraceElement element : values) {
				System.out.println("thread=" + key.getName() 
						+ "\t" + element.getClassName()   
	                    + "\t" + element.getFileName()   
	                    + "\t" + element.getLineNumber()  
	                    + "\t" + element.getMethodName()); 
				//System.out.println(element);
			}
		}
	}

}
