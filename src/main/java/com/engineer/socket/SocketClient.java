package com.engineer.socket;

import java.io.IOException;
import java.net.Socket;

import javax.net.SocketFactory;

public class SocketClient {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 1; i++) {
			Socket socket = SocketFactory.getDefault().createSocket("localhost", 10001);
			new ClientThread(socket, i).start();
		}
		
//		Socket socket = SocketFactory.getDefault().createSocket("localhost", 10001);
//		PrintWriter ut = new PrintWriter(socket.getOutputStream(),true);
//		
//		int i = 0;
//		String message = "Test " + i;
//		ut.println(message);
//		System.out.println(String.format("[%s] Send Message in client: %s - <%s>", Thread.currentThread().getName(), i, message));
//		socket.shutdownOutput();
//		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		String response = reader.readLine();
//		System.out.println(String.format("[%s] Receive Message in client: %s - <%s>", Thread.currentThread().getName(), i, response));
//		
//		ut.close();
//		reader.close();
//		
//		Thread.sleep(6000);
	}

}
