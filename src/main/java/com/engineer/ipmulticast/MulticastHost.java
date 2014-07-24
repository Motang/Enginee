package com.engineer.ipmulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class MulticastHost {

	public static int port = 1234;
	public static String host = "224.5.6.7";
	
	public static void main(String[] args) {
		InetAddress group = null;
		MulticastSocket mutilcastSocket = null;
		
		try {
			group = InetAddress.getByName(host);
			mutilcastSocket = new MulticastSocket(port);
			mutilcastSocket.setBroadcast(true);
			
			mutilcastSocket.joinGroup(group);
			mutilcastSocket.setLoopbackMode(false);
			
			byte[] buffer = new byte[1024];
			while (true) {  
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				mutilcastSocket.receive(dp);
				
				String request = new String(dp.getData(), 0, dp.getLength());
				System.out.println(request);
				Arrays.fill(buffer, (byte)0);
				
				//byte[] response = ("response:" + request).getBytes();
				//DatagramPacket rdp = new DatagramPacket(response, response.length, group, port);
				//mutilcastSocket.send(rdp);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {  
            if (mutilcastSocket != null && group != null) {  
                try {  
                    mutilcastSocket.leaveGroup(group); 
                    mutilcastSocket.close(); 
                } catch (IOException e) {  
                }  
            }  
        }  
	}

}
