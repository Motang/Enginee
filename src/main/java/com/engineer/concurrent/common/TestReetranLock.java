package com.engineer.concurrent.common;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//原来Condtion.await()的时候线程A会允许其他线程来抢夺锁，当线程A的阻塞状态被唤醒后，线程A会重新去抢夺锁。
public class TestReetranLock {
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	final Object[] items = new Object[100];
	int putptr, takeptr, count;

	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length){
				notFull.await();
			}
			items[putptr] = x;
			if (++putptr == items.length)
				putptr = 0;
			++count;
			System.out.printf("%s: put: %d\n",Thread.currentThread().getName(),count);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0){
				notEmpty.await();
			}
			Object x = items[takeptr];
			if (++takeptr == items.length)
				takeptr = 0;
			--count;
			System.out.printf("%s: take: %d\n",Thread.currentThread().getName(),count);
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		//原来Condtion.await()的时候线程A会允许其他线程来抢夺锁，当线程A的阻塞状态被唤醒后，线程A会重新去抢夺锁。
		final TestReetranLock lock = new TestReetranLock();
		Runnable producer = new Runnable(){
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						lock.put(i);
						Random random=new Random();
						Thread.sleep(random.nextInt(100));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}};
		Runnable consummer = new Runnable(){
					@Override
					public void run() {
						for (int i = 0; i < 100; i++) {
							try {
								lock.take();
								Random random=new Random();
								Thread.sleep(random.nextInt(100));
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
			}};
			
		Thread thread1 = new Thread(producer);
		Thread thread2 = new Thread(producer);
		Thread thread3 = new Thread(producer);
		Thread thread4 = new Thread(producer);
		
		thread1.start();
		thread2.start();
		
		Thread t1 = new Thread(consummer);
		t1.start();
		
		try {
			thread1.join();
			thread2.join();
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
