package com.engineer.concurrent.common;

import java.util.concurrent.PriorityBlockingQueue;

public class TestPriorityBlockingQueue {

	public class TaskEvent implements Comparable<TaskEvent>{

		private String thread;
		private int priority;
		
		public TaskEvent(String thread, int priority) {
			super();
			this.thread = thread;
			this.priority = priority;
		}
		
		public String getThread() {
			return thread;
		}

		public int getPriority() {
			return priority;
		}

		@Override
		public int compareTo(TaskEvent o) {
			if (priority > o.getPriority()) {
				return -1;
			} else if (priority < o.getPriority()){
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
	public class AddTask implements Runnable{
		private PriorityBlockingQueue<TaskEvent> queue;

		public AddTask(PriorityBlockingQueue<TaskEvent> queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			for (int i = 0; i < 10000; i++) {
				TaskEvent e = new TaskEvent(name, i);
				this.queue.add(e);
			}
		}
	}
	public class PollTask implements Runnable{
		private PriorityBlockingQueue<TaskEvent> queue;
		
		public PollTask(PriorityBlockingQueue<TaskEvent> queue) {
			super();
			this.queue = queue;
		}
		
		@Override
		public void run() {
			System.out.printf("befor Queue size is %d \n", this.queue.size());
			String name = Thread.currentThread().getName();
			for (int i = 0; i < 10000; i++) {
				TaskEvent e = this.queue.poll();
				System.out.printf("Queue threadName=%s, name=%s, priority=%d \n", name, e.getThread(), e.getPriority());
			}
			
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestPriorityBlockingQueue test = new TestPriorityBlockingQueue();
		PriorityBlockingQueue<TaskEvent> queue = new PriorityBlockingQueue<TaskEvent>();
		
		Thread[] addTasks = new Thread[10];
		for (int i = 0; i < addTasks.length; i++) {
			AddTask addTask = test.new AddTask(queue);
			addTasks[i] = new Thread(addTask, "Add Task"+i);
			
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
			pollTasks[i] = new Thread(pollTask, "Poll Task"+i);
			
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
