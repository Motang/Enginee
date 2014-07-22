package com.engineer.concurrent.common;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestProducerConsummer {
	public class EventStorage {
		private int maxSize = 10;
		private List<Date> storage = new LinkedList<Date>();

		public synchronized void set() {
			while (storage.size() >= maxSize) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			((LinkedList<Date>) storage).offer(new Date());
			System.out.printf("Set: %d \n", storage.size());
			notifyAll();
		}

		public synchronized void get() {
			while (storage.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.printf("Get: %d: %s \n", storage.size(), ((LinkedList<?>) storage).poll());
			notifyAll();
		}
		
		public int getSize(){
			return storage.size();
		}
	}

	public class Producer implements Runnable {
		private EventStorage storage;

		public Producer(EventStorage storage) {
			this.storage = storage;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				storage.set();
				try {
					TimeUnit.MILLISECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public class Consumer implements Runnable {
		private EventStorage storage;

		public Consumer(EventStorage storage) {
			this.storage = storage;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				//while(true)
				storage.get();
				try {
					TimeUnit.MILLISECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int a = 1000000000;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestProducerConsummer test = new TestProducerConsummer();
		
		EventStorage storage = test.new EventStorage();
		
		Producer producer = test.new Producer(storage);
		Thread thread1 = new Thread(producer);
		Thread thread2 = new Thread(producer);
		Thread thread3 = new Thread(producer);
		Thread thread4 = new Thread(producer);
		Thread thread5 = new Thread(producer);

		Consumer consumer = test.new Consumer(storage);
		Thread thread11 = new Thread(consumer);
		Thread thread12 = new Thread(consumer);
		Thread thread13 = new Thread(consumer);
		Thread thread14 = new Thread(consumer);
		Thread thread15 = new Thread(consumer);

		thread11.start();
		thread12.start();
		thread13.start();
		thread14.start();
		thread15.start();
		thread5.start();
		thread4.start();
		thread3.start();
		thread2.start();
		thread1.start();
		
		try {
			thread1.join();
			thread11.join();
			thread2.join();
			thread12.join();
			thread3.join();
			thread13.join();
			thread4.join();
			thread14.join();
			thread5.join();
			thread15.join();
			
			System.out.println(storage.getSize());
			TimeUnit.HOURS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
