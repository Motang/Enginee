package com.engineer.concurrent.common;

import java.lang.Thread.UncaughtExceptionHandler;

public class TestThreadException {

	public class ExceptionHandler implements UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			System.out.printf("An exception has been captured\n");
			System.out.printf("Thread: %s\n", t.getId());
			System.out.printf("Exception: %s: %s\n", e.getClass().getName(), e.getMessage());
			System.out.printf("Stack Trace: \n");
			e.printStackTrace(System.out);
			System.out.printf("Thread status: %s\n", t.getState());
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					int numero=Integer.parseInt("TTT");
				}
			}
		});
		thread.setUncaughtExceptionHandler(new TestThreadException().new ExceptionHandler());
		thread.start();

	}

}
