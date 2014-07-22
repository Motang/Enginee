package com.engineer.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

public class SocketServer {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket server = ServerSocketFactory.getDefault().createServerSocket(10001);
		
		int i = 0;
		try {
			while (true) {
				Socket incoming = server.accept();
				System.out.println("[Main] ====>1");
				ServerThread serverThread = new ServerThread(incoming,i++);
				serverThread.start();
			}
		} finally {
			server.close();
		}

	}
	
}
