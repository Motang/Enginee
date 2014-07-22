package com.engineer.concurrent.common;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestThreads {

	public class DataSourcesLoader implements Runnable {
		public void run() {
			System.out.printf("%s - Beginning data sources loading: %s\n", Thread.currentThread().getName(), new Date());
			
			try {
			    TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			
			System.out.printf("%s - Data sources loading has finished:%s\n", Thread.currentThread().getName(), new Date());
		}
	}
	
	public static void main(String[] args) {
		TestThreads testThreads = new TestThreads();
		
		DataSourcesLoader dsLoader = testThreads.new DataSourcesLoader();
		DataSourcesLoader ncLoader = testThreads.new DataSourcesLoader();
		
		Thread thread1 = new Thread(dsLoader,"DatasourceConnectionLoader");
		Thread thread2 = new Thread(ncLoader,"NetworkConnectionLoader");
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.printf("Main: Configuration has been loaded: %s\n",new Date());
		

	}
}
