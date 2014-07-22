package com.engineer.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	private Socket incoming;
	private int i;

	public ClientThread(Socket incoming, int i) {
		super();
		this.incoming = incoming;
		this.i = i;
	}

	@Override
	public void run() {
		try {
			PrintWriter ut = new PrintWriter(incoming.getOutputStream(),true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			
			String message = "Test " + i;
			ut.println(message);
			System.out.println(String.format("[%s] Send Message in client: %s - <%s>", Thread.currentThread().getName(), i, message));
			incoming.shutdownOutput();
			
			String response = reader.readLine();
			System.out.println(String.format("[%s] Receive Message in client: %s - <%s>", Thread.currentThread().getName(), i, response));
			
			ut.close();
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				incoming.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
