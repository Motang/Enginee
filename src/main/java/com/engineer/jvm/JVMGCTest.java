package com.engineer.jvm;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class JVMGCTest {

	/*
	 * 测试GC回收器的参数设置
	 * 
	 * -Xms256M -Xmx256M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintCommandLineFlags
	 * -Xms256M -Xmx256M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintCommandLineFlags -XX:+UseSerialGC
	 * -Xms256M -Xmx256M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintCommandLineFlags -XX:+UseParallelGC
	 * -Xms256M -Xmx256M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC
	 * -Xms256M -Xmx256M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC
	 * -Xms256M -Xmx256M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC -XX:-UseParNewGC
	 */
	public static void main(String[] args) {
		ByteBuffer allocate = ByteBuffer.allocate(1024 * 100);
		
		for (int i = 0; i < 1000; i++) {
			allocate = ByteBuffer.allocate(1024 * 1024 * 100);
			try {
				TimeUnit.SECONDS.sleep(3);
				allocate.clear();
				System.out.println(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

}
