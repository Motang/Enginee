package com.engineer.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import sun.misc.Unsafe;

/*
 * 在Java Concurrency in Practice中是这样定义线程安全的：
 * 当多个线程访问一个类时，如果不用考虑这些线程在运行时环境下的调度和交替运行，并且不需要额外的同步及在调用方代码不必做其他的协调，这个类的行为仍然是正确的，那么这个类就是线程安全的。
 * 显然只有资源竞争时才会导致线程不安全，因此无状态对象永远是线程安全的。
 * 
 * 原子操作的描述是： 多个线程执行一个操作时，其中任何一个线程要么完全执行完此操作，要么没有执行此操作的任何步骤，那么这个操作就是原子的。
 * 
 * 
 * 在JDK 5之前Java语言是靠synchronized关键字保证同步的，这会导致有锁（后面的章节还会谈到锁）。
锁机制存在以下问题：
（1）在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，引起性能问题。
（2）一个线程持有锁会导致其它所有需要此锁的线程挂起。
（3）如果一个优先级高的线程等待一个优先级低的线程释放锁会导致优先级倒置，引起性能风险。
volatile是不错的机制，但是volatile不能保证原子性。因此对于同步最终还是要回到锁机制上来。

独占锁是一种悲观锁，synchronized就是一种独占锁，会导致其它所有需要锁的线程挂起，等待持有锁的线程释放锁。
而另一个更加有效的锁就是乐观锁。所谓乐观锁就是，每次不加锁而是假设没有冲突而去完成某项操作，如果因为冲突失败就重试，直到成功为止。

CAS 操作
上面的乐观锁用到的机制就是CAS，Compare and Swap。
CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。
 */
public class ConcurrentTest {
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		condition.await();//similar to wait()
		condition.signal();//similar to notify()
		condition.signalAll();//similar to notifyAll()
		
		Semaphore semaphore = new Semaphore(10);
		semaphore.acquire();
		
		TimeUnit.SECONDS.sleep(1000);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
		cyclicBarrier.await();
	}
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	volatile int value;
	private static final Unsafe unsafe = Unsafe.getUnsafe();
	private static final long valueOffset;

    static {
      try {
        valueOffset = unsafe.objectFieldOffset
            (AtomicInteger.class.getDeclaredField("value"));
      } catch (Exception ex) { throw new Error(ex); }
    }
	
	void testCAS() {
		
	}
	
	public final int incrementAndGet() {
	    for (;;) {
	        int current = get();
	        int next = current + 1;
	        if (compareAndSet(current, next))
	            return next;
	    }
	}
	
	public final boolean compareAndSet(int expect, int update) {   
	    return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
	}
	public final int get() {
        return value;
    }
}
