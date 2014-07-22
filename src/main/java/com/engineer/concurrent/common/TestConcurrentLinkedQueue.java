package com.engineer.concurrent.common;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TestConcurrentLinkedQueue {

	public class AddTask implements Runnable{
		private Queue<String> queue;

		public AddTask(Queue<String> queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			for (int i = 0; i < 10000; i++) {
				this.queue.add("name:" + name + " - element:"+ i);
				//this.queue.offer("name:" + name + " - element:"+ i);
			}
		}
	}
	public class PollTask implements Runnable{
		private Queue<String> queue;
		
		public PollTask(Queue<String> queue) {
			super();
			this.queue = queue;
		}
		
		@Override
		public void run() {
			System.out.printf("befor Queue size is %d \n", this.queue.size());
			for (int i = 0; i < 10000; i++) {
				this.queue.poll();
				//this.queue.element();
				//this.queue.peek();
			}
			
			System.out.printf("Queue size is %d \n", this.queue.size());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestConcurrentLinkedQueue test = new TestConcurrentLinkedQueue();
		Queue<String> queue = new ConcurrentLinkedQueue<String>();
		
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
