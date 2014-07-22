package com.engineer.concurrent.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestCallable {
	
	public class FactoricalCalculator implements Callable<Integer>{
		private Integer number;
		
		public FactoricalCalculator(Integer number) {
			super();
			this.number = number;
		}

		public Integer call() throws Exception {
			int result = 1;
			if (number>0) {
				for (int i = 2; i <= number; i++) {
					result *= i;
					TimeUnit.MILLISECONDS.sleep(2000);
				}
			}
			
			System.out.printf("%s for calculator %s \n", Thread.currentThread().getName(), number);
			return result;
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestCallable test = new TestCallable();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
		List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();
		
		Random random = new Random();
//		for (int i = 0; i < 10; i++) {
//			int number = random.nextInt(10);
//			FactoricalCalculator calculator = test.new FactoricalCalculator(number);
//			Future<Integer> result = executor.submit(calculator);
//			
//			resultList.add(result);
//		}
		
		List<FactoricalCalculator> calculatorList = new ArrayList<FactoricalCalculator>();
		for (int i = 0; i < 10; i++) {
			int number = random.nextInt(10);
			FactoricalCalculator calculator = test.new FactoricalCalculator(number);
			
			calculatorList.add(calculator);
		}
		try {
			resultList = executor.invokeAll(calculatorList);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		do {
//			int i = 0;
//			for (Future<Integer> future : resultList) {
//				System.out.printf("Task %d: %s \n",i++, future.isDone());
//			}
//			
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} while (executor.getCompletedTaskCount()<resultList.size());
		
		int i = 0;
		for (Future<Integer> future : resultList) {
			try {
				System.out.printf("Task %d: result=%d \n",i++, future.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
	}

}
