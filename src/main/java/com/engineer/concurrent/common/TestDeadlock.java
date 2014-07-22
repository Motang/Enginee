package com.engineer.concurrent.common;

import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDeadlock {
	public static void main(String[] args) {
		System.setProperty("log4j.configuration", "log4j_test.properties");
		final Logger logger = LoggerFactory.getLogger(TestDeadlock.class);
		
		CodeSource codeSource = TestDeadlock.class.getProtectionDomain().getCodeSource();
		System.out.println(codeSource);
		// These are the two resource objects we'll try to get locks for
		final Object resource1 = "resource1";
		final Object resource2 = "resource2";
		int a = 0;
		// Here's the first thread. It tries to lock resource1 then resource2
		Thread t1 = new Thread("thread one") {
			public void run() {
				// Lock resource 1
				synchronized (resource1) {
					logger.debug("Thread 1: locked resource 1");
					// Pause for a bit, simulating some file I/O or
					// something. Basically, we just want to give the
					// other thread a chance to run. Threads and deadlock
					// are asynchronous things, but we're trying to force
					// deadlock to happen here...
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					// Now wait 'till we can get a lock on resource 2
					synchronized (resource2) {
						while (true) {
							logger.debug("Thread 1: locked resource 2");
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
							}
						}
					}
				}
			}
		};

		// Here's the second thread. It tries to lock resource2 then resource1
		Thread t2 = new Thread("thread second") {
			public void run() {
				// This thread locks resource 2 right away
				synchronized (resource2) {
					logger.debug("Thread 2: locked resource 2");
					// Then it pauses, just like the first thread.
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}

					// Then it tries to lock resource1. But wait! Thread
					// 1 locked resource1, and won't release it 'till it
					// gets a lock on resource2. This thread holds the
					// lock on resource2, and won't release it 'till it
					// gets resource1. We're at an impasse. Neither
					// thread can run, and the program freezes up.
					synchronized (resource1) {
						logger.debug("Thread 2: locked resource 1");
					}
				}
			}
		};

		// Start the two threads. If all goes as planned, deadlock will occur,
		// and the program will never exit.
		t1.start();
		t2.start();
		
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Object> v = new ArrayList<Object>();
		while (true){
			for (int i = 1; i < 10000; i++) {
				Object o = new Object();
				v.add(o);
				o = null;
			}
			//Integer.MAX_VALUE
			if (v.size() >= 2000000000 ) {
				break;
			} else {
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.debug("=={}", v.size());
			}
		}
		logger.debug("{}", v.size());
	}
}
