package com.engineer.ipmulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient implements Runnable{
	private String message;
	
	public MulticastClient(String message) {
		super();
		this.message = message;
	}

	public static void main(String[] args) throws InterruptedException {
		String message = "Hello World ";
		Thread multicastClient1 = new Thread(new MulticastClient(message));
		Thread multicastClient2 = new Thread(new MulticastClient(message));
		
		multicastClient1.start();
		multicastClient2.start();
		
		multicastClient1.join();
		multicastClient1.join();
	}

	
	
	public void run() {
		InetAddress group = null;
		MulticastSocket mcs = null;
		int port = MulticastHost.port;
		try {
			group = InetAddress.getByName(MulticastHost.host);
			mcs = new MulticastSocket(MulticastHost.port);
			mcs.setBroadcast(true);
			
			mcs.joinGroup(group);
			
			String threadName = Thread.currentThread().getName();
			while (true) { 
				String tempMessage =  threadName + " - " + message + " - " + new java.util.Date();
	            byte[] buffer = tempMessage.getBytes(); 
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length, group, port);
				
				mcs.send(dp);
				
				
				System.out.println(threadName + " - 发送数据包给" + group + ":" + port);  
                Thread.sleep(1000);  
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
            if (mcs != null && group != null) {  
                try {  
                    mcs.leaveGroup(group); 
                    mcs.close(); 
                } catch (IOException e) {  
                }  
            }  
        }
	}
}
