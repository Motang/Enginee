package com.engineer.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestExecutorService {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		//ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
//		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		System.out.printf("1Server - Core pool size=%d \n", executor.getCorePoolSize());
		System.out.printf("1Server - Pool size=%d \n", executor.getPoolSize());
		System.out.printf("1Server - Active count=%d \n", executor.getActiveCount());
		System.out.printf("1Server - Completed count=%d \n", executor.getCompletedTaskCount());
		//ExecutorService service = Executors.newSingleThreadExecutor();
		    for(int i=0;i<10;i++){
		     executor.execute(new MyExecutor(i));
		     //service.submit(new MyExecutor(i));
		    }
		    
		    TimeUnit.SECONDS.sleep(10);
		    System.out.printf("Server - Core pool size=%d \n", executor.getCorePoolSize());
		    System.out.printf("Server - Pool size=%d \n", executor.getPoolSize());
		    System.out.printf("Server - Lasgest pool size=%d \n", executor.getLargestPoolSize());
		    System.out.printf("Server - Maximum pool size=%d \n", executor.getMaximumPoolSize());
		    System.out.printf("Server - Active count=%d \n", executor.getActiveCount());
		    System.out.printf("Server - Completed count=%d \n", executor.getCompletedTaskCount());
		    System.out.printf("Server - Task count=%d \n", executor.getTaskCount());
		    System.out.println("submit finish");
		    executor.shutdown();
		    System.out.println("submit finish 2");
		    //service.execute(new MyExecutor(10));
		    //service.awaitTermination(10, TimeUnit.MINUTES);
		    //System.out.println("submit finish 3");

	}
	
	public static class MyExecutor extends Thread {
		private int index;
		public MyExecutor(int i){
		    this.index=i;
		}
		public void run(){
		    try{
		     System.out.println("["+this.index+"] start....");
		     TimeUnit.SECONDS.sleep((long) (Math.random() * 7));
		     System.out.println("["+this.index+"] end.");
		    }
		    catch(Exception e){
		     e.printStackTrace();
		    }
		}
	}


}
