package com.engineer.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ChannelClient {
	public void start() throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("localhost",9999));
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		Scanner scanner = new Scanner(System.in,"UTF-8");
		System.out.println(Charset.defaultCharset());
		
		while(!Thread.interrupted()){
			// 轮询访问selector  
			selector.select();
			// 获得selector中选中的项的迭代器 
			Set<SelectionKey> selectedKeys2 = selector.selectedKeys();
			Iterator<SelectionKey> selectedKeys = selectedKeys2.iterator();
			System.out.println("client have " + selectedKeys2.size() + " active events");
			
			while (selectedKeys.hasNext()) {
				SelectionKey selectionKey = selectedKeys.next();
				// 删除已选的key,以防重复处理  
				selectedKeys.remove();
				
				SocketChannel channel = (SocketChannel) selectionKey.channel(); 
				
				// 连接事件发生 
				if (selectionKey.isConnectable()) {
					if(channel.isConnectionPending()){
						channel.finishConnect(); 
						System.out.println("server connected..."); 
					}
					
					 // 设置成非阻塞  
                    channel.configureBlocking(false);
                    //在这里可以给服务端发送信息哦  
                    String message = new String("向服务端发送了一条信息");
					channel.write(ByteBuffer.wrap(message.getBytes()));  
                    //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。  
                    channel.register(selector, SelectionKey.OP_READ);
                    
					System.out.println("CONNECT====>>Client send: "+ message); 
				}
				
				if (selectionKey.isWritable()) {
					System.out.println("please input message"); 
                    String message = scanner.nextLine(); 
					//String message ="I am testing client";
                    ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes()); 
                    socketChannel.write(writeBuffer);
                    socketChannel.register(selector, SelectionKey.OP_READ); 
                    System.out.println("Writable====>>Client send: "+ message);
				}
				
				if (selectionKey.isReadable()) {
					ByteBuffer dsts = ByteBuffer.allocate(100);
					socketChannel.read(dsts);
					dsts.flip();
					byte[] dst = new byte[dsts.limit()];
					dsts.get(dst);
//					dsts.flip();
					socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE); 
					System.out.println("Readable<<====Server Said: "+ new String(dst)); 
				}
				
				if(!selectionKey.isValid()) {
					System.out.println("=====close=====");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new ChannelClient().start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
