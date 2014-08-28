package com.engineer.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

	/*
	 * 从相对简单的Atomic入手（java.util.concurrent是基于Queue的并发包，而Queue，很多情况下使用到了Atomic操作，
	 * 因此首先从这里开始）。很多情况下我们只是需要一个简单的、高效的、线程安全的递增递减方案。
	 * 注意，这里有三个条件：
	 * 1. 简单，意味着程序员尽可能少的操作底层或者实现起来要比较容易；
	 * 2. 高效意味着耗用资源要少，程序处理速度要快；
	 * 3. 线程安全也非常重要，这个在多线程下能保证数据的正确性。
	 * 这三个条件看起来比较简单，但是实现起来却难以令人满意。
	 *
	 * 通常情况下，在Java里面，++i或者--i不是线程安全的，这里面有三个独立的操作：
	 * 或者变量当前值，为该值+1/-1，然后写回新的值。在没有额外资源可以利用的情况下，只能使用加锁才能保证读-改-写这三个操作时“原子性”的。
	 */

	//@Test
	public void testAll() throws InterruptedException {
		final AtomicInteger value = new AtomicInteger(10);
		assertEquals(value.compareAndSet(1, 2), false);
		assertEquals(value.get(), 10);
		assertTrue(value.compareAndSet(10, 3));
		assertEquals(value.get(), 3);
		value.set(0);
		//
		assertEquals(value.incrementAndGet(), 1);
		assertEquals(value.getAndAdd(2), 1);
		assertEquals(value.getAndSet(5), 3);
		assertEquals(value.get(), 5);
		//
		final int threadSize = 100000;
		Thread[] ts = new Thread[threadSize];
		for (int i = 0; i < threadSize; i++) {
			ts[i] = new Thread() {
				public void run() {
					value.incrementAndGet();
				}
			};
		}
		//
		for (Thread t : ts) {
			t.start();
		}
		for (Thread t : ts) {
			t.join();
		}
		//
		assertEquals(value.get(), 5 + threadSize);
	}

	
	int value = 0;
	//@Test
	public void compare() throws InterruptedException {
		final int threadSize = 100000;
		Thread[] ts = new Thread[threadSize];
		for (int i = 0; i < threadSize; i++) {
			ts[i] = new Thread() {
				public void run() {
					++value;
				}
			};
		}
		//
		for (Thread t : ts) {
			t.start();
		}
		for (Thread t : ts) {
			t.join();
		}
		
		System.out.println(value);
		assertEquals(value, threadSize);
	}
}
