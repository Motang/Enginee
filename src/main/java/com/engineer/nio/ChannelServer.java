package com.engineer.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;

public class ChannelServer implements Runnable{
	
	private Selector selector;
	private Charset charset = Charset.forName("GBK");
	private CharsetDecoder decoder = charset.newDecoder();
	private CharsetEncoder encoder = charset.newEncoder();
	
	public ChannelServer() throws IOException {
		ServerSocketChannel server = ServerSocketChannel.open();//初始化服务器Channel对象
		//将服务器Channel设置为非阻塞模式
		server.configureBlocking(false);
		ServerSocket socket = server.socket();//获取服务器Channel对应的ServerSocket对象
		//把Socket绑定到监听端口9999上
		socket.bind(new InetSocketAddress(9999)); 
		
		selector = Selector.open(); //初始化Selector对象 
		//将服务器Channel注册到Selector对象，并指出服务器Channel所感兴趣的事件为可接受请求操作
		server.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	public void run() {
		while(true) {
			try {
				/** 
				*应用Select机制轮循是否有用户感兴趣的新的网络事件发生，当没有 
				* 新的网络事件发生时，此方法会阻塞，直到有新的网络事件发生为止 
				*/ 
				selector.select();
			} catch (Exception e) {
				continue; //当有异常发生时，继续进行循环操作
			}
			
			/** 
			* 得到活动的网络连接选择键的集合 
			*/
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			int activeSockets = selectedKeys.size();//获取活动连接的数目
			System.out.println(String.format("===>>There are %s active connections", activeSockets));
			
			if(activeSockets==0) { 
				continue;//如果连接数为0，则继续进行循环操作 
			} 
			
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				// 删除已选的key,以防重复处理  
				iterator.remove(); 
				
				if (!key.isValid() && !key.isConnectable()) {
					System.out.println("=====close=====");
					return;
				}
				
				// 客户端请求连接事件 
				/** 
				* 如果关键字状态是为可接受，则接受连接，注册通道，以接受更多的
				* 事件，进行相关的服务器程序处理 
				*/ 
				if (key.isAcceptable()) {
					doServerSocketEvent(key);
					continue;
				}
				
				/** 
				* 如果关键字状态为可读，则说明Channel是一个客户端的连接通道， 
				* 进行相应的读取客户端数据的操作 
				*/ 
				if (key.isReadable()) {
					doClientReadEvent(key);
					continue;
				}
				
				/** 
				* 如果关键字状态为可写，则也说明Channel是一个客户端的连接通道， 
				* 进行相应的向客户端写数据的操作 
				*/ 
				if (key.isWritable()) {
					doClinetWriteEvent(key);
					continue;
				}
			}
		}
	}
	
	/** 
	* 处理服务器事件操作 
	* @param key 服务器选择键对象 
	*/ 
	private void doServerSocketEvent(SelectionKey key) {
		SocketChannel client = null;
		try {
			ServerSocketChannel severChannel = (ServerSocketChannel)key.channel();
			client = severChannel.accept();
			if (client == null) {
				return;
			}
			System.out.println("=========Acceptable=====");
			//将客户端Channel设置为非阻塞型
			client.configureBlocking(false); 
			//在这里可以给客户端发送信息哦  
			String message = "向客户端发送了一条信息";
			client.write(ByteBuffer.wrap(message.getBytes()));  
			System.out.println("Acceptable====>>Server send: "+message);
			//在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置所感兴趣的事件为读的权限。
			int ops = SelectionKey.OP_READ;//|SelectionKey.OP_WRITE;
			//System.out.println(ops);
			client.register(selector, ops); 
		} catch (Exception e) {
			if (client!=null)
				try {
					client.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		}
	}

	private void doClinetWriteEvent(SelectionKey key) {
		SelectableChannel channel = key.channel();
		if (channel == null) {
			return;
		}
		System.out.println("=========Writable=====");
//		try {
//			if (channel.isOpen() && channel instanceof SocketChannel) {
//				System.out.println("please input message in Sever"); 
//				Scanner scanner = new Scanner(System.in);
//				String message = scanner.nextLine(); 
//				ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes()); 
//				SocketChannel severChannel = (SocketChannel)channel;
//				severChannel.write(writeBuffer);
//				severChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("ClinetWrite");
		
	}

	private void doClientReadEvent(SelectionKey key) {
		SelectableChannel channel = key.channel();
		if (channel == null) {
			return;
		}
		System.out.println("=========Readable=====");
		SocketChannel client = (SocketChannel) channel;
		if (client.isConnected()&&!client.socket().isClosed()) {
			
			try {
				ByteBuffer dsts = ByteBuffer.allocate(100);
				client.read(dsts);
				//byte[] dst = new byte[dsts.position()];
				//dsts.rewind();
				//dsts.get(dst);
				dsts.flip();
				String string = new String(dsts.array());
				System.out.println("Readable<<====Client Said: "+ string);
				ByteBuffer outBuffer = ByteBuffer.wrap("OK".getBytes());
				client.write(outBuffer);// 将消息回送给客户端
				System.out.println("Readable====>>Server send: OK");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				try {
					client.close();
					selector.close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			System.out.println("ClientRead");
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new ChannelServer().run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
