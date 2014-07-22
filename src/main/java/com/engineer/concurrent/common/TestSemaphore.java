package com.engineer.concurrent.common;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSemaphore {

	/*
	 * 是semaphores也可以用来保护多个资源的副本，也就是说当你有一个代码片段每次可以被多个线程执行。在这个指南中，
	 * 你将学习怎样使用semaphore来保护多个资源副本。你将实现的例子会有一个print queue但可以在3个不同的打印机上打印文件。
	 * 
	 * Semaphore类有另2个版本的 acquire() 方法：
	 * acquireUninterruptibly()：acquire()方法是当semaphore的内部计数器的值为0时，阻塞线程直到semaphore被释放。
	 * 在阻塞期间，线程可能会被中断，然后此方法抛出InterruptedException异常。而此版本的acquire方法会忽略线程的中断而且不会抛出任何异常
	 * 。 tryAcquire()：此方法会尝试获取semaphore。如果成功，返回true。如果不成功，返回false值，
	 * 并不会被阻塞和等待semaphore的释放。接下来是你的任务用返回的值执行正确的行动。
	 */
	public class PrintQueue {
		// 1. 如我们之前提到的，你将实现semaphores来修改print queue例子。打开PrintQueue类并声明一个boolean
		// array名为 freePrinters。这个array储存空闲的等待打印任务的和正在打印文档的printers。
		private boolean freePrinters[];
		private Semaphore semaphore;
		// 2. 接着，声明一个名为lockPrinters的Lock对象。将要使用这个对象来保护freePrinters array的访问。
		private Lock lockPrinters;

		// 3. 修改类的构造函数并初始化新声明的对象们。freePrinters array
		// 有3个元素，全部初始为真值。semaphore用3作为它的初始值。
		public PrintQueue() {
			semaphore = new Semaphore(3);
			freePrinters = new boolean[3];

			for (int i = 0; i < 3; i++) {
				freePrinters[i] = true;
			}
			lockPrinters = new ReentrantLock();
		}

		// 4. 修改printJob()方法。它接收一个称为document的对象最为唯一参数。
		public void printJob(Object document) {
			// 5. 首先，调用acquire()方法获得semaphore的访问。由于此方法会抛出
			// InterruptedException异常，所以必须加入处理它的代码。
			try {
				semaphore.acquire();
				// 6. 接着使用私有方法 getPrinter()来获得被安排打印任务的打印机的号码。
				int assignedPrinter = getPrinter();
				// 7. 然后， 随机等待一段时间来实现模拟打印文档的行。
				long duration = (long) (Math.random() * 10);
				System.out.printf(
						"%s: PrintQueue: Printing a Job in Printer%d during %d seconds\n", Thread
								.currentThread().getName(), assignedPrinter, duration);
				TimeUnit.SECONDS.sleep(duration);
				// 8. 最后，调用release() 方法来解放semaphore并标记打印机为空闲，通过在对应的freePrinters
				// array引索内分配真值。
				freePrinters[assignedPrinter] = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.release();
			}
		}

		// 9. 实现 getPrinter() 方法。它是一个私有方法，返回一个int值，并不接收任何参数。
		private int getPrinter() {
			// 10. 首先，声明一个int变量来保存printer的引索值。
			int ret = -1;
			// 11. 然后， 获得lockPrinters对象 object的访问。
			try {
				lockPrinters.lock();
				// 12. 然后，在freePrinters
				// array内找到第一个真值并在一个变量中保存这个引索值。修改值为false，因为等会这个打印机就会被使用。
				for (int i = 0; i < freePrinters.length; i++) {
					if (freePrinters[i]) {
						ret = i;
						freePrinters[i] = false;
						break;
					}
				}
				// 13. 最后，解放lockPrinters对象并返回引索对象为真值。
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lockPrinters.unlock();
			}
			return ret;
			// 14. Job 和 Core 类不做任何改变。
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestSemaphore testSemaphore = new TestSemaphore();
		final PrintQueue queue = testSemaphore.new PrintQueue();
		Runnable run = new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					queue.printJob("Run" + i);
				}

			}
		};

		Thread thread1 = new Thread(run);
		Thread thread2 = new Thread(run);
		Thread thread3 = new Thread(run);
		Thread thread4 = new Thread(run);
		Thread thread5 = new Thread(run);

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();

		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
			thread5.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
