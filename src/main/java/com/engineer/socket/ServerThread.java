package com.engineer.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	private Socket incoming;
	private int i;

	public ServerThread(Socket incoming, int i) {
		super();
		this.incoming = incoming;
		this.i = i;
	}

	@Override
	public void run() {
		System.out.println(String.format("[%s] is running with count=%s", Thread.currentThread().getName(), i));
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			String line = reader.readLine();
			while (!"".equals(line) && line != null && !"null".equals(line)) {
				System.out.println(String.format("[%s] Receive client: %s - <<%s>>", Thread.currentThread().getName(), i, line));
				line = reader.readLine();
			}
			
			PrintWriter ut = new PrintWriter(incoming.getOutputStream(),true);
			ut.println(Thread.currentThread().getName() + " get OK");
			incoming.shutdownOutput();
			
			ut.close();
			reader.close();
			System.out.println(String.format("[%s] done", Thread.currentThread().getName()));
			//Thread.sleep(2000);
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
