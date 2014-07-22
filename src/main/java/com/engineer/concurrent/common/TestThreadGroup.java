package com.engineer.concurrent.common;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestThreadGroup {

	public class Result {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public class SearchTask implements Runnable {
		private Result result;

		public SearchTask(Result result) {
			this.result = result;
		}

		public void run() {
			String name = Thread.currentThread().getName();
			System.out.printf("Thread %s: Start\n", name);
			try {
				doTask();
				result.setName(name);
			} catch (InterruptedException e) {
				System.out.printf("Thread %s: Interrupted\n", name);
				return;
			}
			System.out.printf("Thread %s: End\n", name);
		}

		private void doTask() throws InterruptedException {
			Random random = new Random((new Date()).getTime());
			int value = (int) (random.nextDouble() * 100);
			System.out.printf("Thread %s: %d\n", Thread.currentThread().getName(), value);
			TimeUnit.SECONDS.sleep(value);
		}
	}

	public static void main(String[] args) {
		TestThreadGroup testThreadGroup = new TestThreadGroup();
		
		Result result = testThreadGroup.new Result();
		SearchTask searchTask = testThreadGroup.new SearchTask(result);
		
		ThreadGroup threadGroup = new ThreadGroup("Searcher");

		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(threadGroup, searchTask);
			thread.start();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//使用list() 方法写关于 ThreadGroup ob对象信息
		System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
		System.out.printf("Information about the Thread Group\n");
		threadGroup.list();

		//使用 activeCount() 和 enumerate() 方法来获取线程个数和与ThreadGroup对象关联的线程的列表。我们可以用这个方法来获取信息， 例如，每个线程状态
		Thread[] threads = new Thread[threadGroup.activeCount()];
		threadGroup.enumerate(threads);
		for (int i = 0; i < threadGroup.activeCount(); i++) {
			System.out.printf("Thread %s: %s\n", threads[i].getName(), threads[i].getState());
		}

		//调用 waitFinish()方法. 我们等下来实现这方法。它会等待ThreadGroup对象中的其中一个线程结束。
		// 实现 waitFinish() 方法. 它会使用 activeCount() 方法来控制到最后一个线程
		waitFinish(threadGroup);
		
		// 用interrupt() 方法中断组里的其他线程
		threadGroup.interrupt();

	}

	private static void waitFinish(ThreadGroup threadGroup) {
		while (threadGroup.activeCount() > 1) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
