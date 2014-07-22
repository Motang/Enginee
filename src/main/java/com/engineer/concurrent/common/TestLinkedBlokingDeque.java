package com.engineer.concurrent.common;

import java.util.concurrent.LinkedBlockingDeque;

public class TestLinkedBlokingDeque {

	public class AddTask implements Runnable{
		private LinkedBlockingDeque<String> queue;

		public AddTask(LinkedBlockingDeque<String> queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			for (int i = 0; i < 10000; i++) {
				this.queue.add("name:" + name + " - element:"+ i);
			}
		}
	}
	public class PollTask implements Runnable{
		private LinkedBlockingDeque<String> queue;
		
		public PollTask(LinkedBlockingDeque<String> queue) {
			super();
			this.queue = queue;
		}
		
		@Override
		public void run() {
			System.out.printf("befor Queue size is %d \n", this.queue.size());
			for (int i = 0; i < 5000; i++) {
				this.queue.pollFirst();
				this.queue.pollLast();
			}
			
			System.out.printf("Queue size is %d \n", this.queue.size());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestLinkedBlokingDeque test = new TestLinkedBlokingDeque();
		LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<String>();
		
		Thread[] addTasks = new Thread[10];
		for (int i = 0; i < addTasks.length; i++) {
			AddTask addTask = test.new AddTask(queue);
			addTasks[i] = new Thread(addTask, "Add Task");
			
			addTasks[i].start();
		}
		
		for (int i = 0; i < addTasks.length; i++) {
			try {
				addTasks[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Thread[] pollTasks = new Thread[10];
		for (int i = 0; i < pollTasks.length; i++) {
			PollTask pollTask = test.new PollTask(queue);
			pollTasks[i] = new Thread(pollTask, "Poll Task");
			
			pollTasks[i].start();
		}
		
		for (int i = 0; i < pollTasks.length; i++) {
			try {
				pollTasks[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
