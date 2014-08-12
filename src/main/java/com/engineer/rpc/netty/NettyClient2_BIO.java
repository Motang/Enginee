package com.engineer.rpc.netty;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engineer.rpc.RPCClient;
import com.engineer.rpc.RPCHello;

public class NettyClient2_BIO implements RPCClient {
	static Logger logger = LoggerFactory.getLogger(NettyClient2_BIO.class);
	
	private String host;  
    private int port;
    private int timeout = 30000; //thirty seconds
    
    private Socket socket; 
    
    
	public NettyClient2_BIO(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	@Override
	public void start() {
	}
	
	public void close() throws IOException {
		socket.close();
	}
	
	
	
	public static void main(String[] args) {
		NettyClient2_BIO client = new NettyClient2_BIO("localhost",8099);
		
		try {
//			final RPCHello proxy = NettyClientHelper.getProxy(client, RPCHello.class);
//			String sayHello = proxy.sayHello("Test");
//			System.out.println(sayHello);
			
			try {
				final RPCHello proxy = NettyClientHelper.getProxy(client, RPCHello.class);
			    Runnable target = new Runnable(){
	                @Override
	                public void run() {
	                    for (int i = 0; i < 5; i++) {
	                    	logger.info("Print Resut ------- {}", proxy.sayHello(Thread.currentThread().getName()+"-Morly"+i));
	                        try {
	                            TimeUnit.SECONDS.sleep(1);
	                        } catch (InterruptedException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                    logger.info("Print Resut ------- {}", proxy.sayHello("DONE"));
	                }};
	            Thread thread1 = new Thread(target, "Test1");
	            Thread thread2 = new Thread(target, "Test2");
	            Thread thread3 = new Thread(target, "Test3");
	            Thread thread4 = new Thread(target, "Test4");
	            Thread thread5 = new Thread(target, "Test5");
	            
	            thread1.start();
	            thread2.start();
	            thread3.start();
	            thread4.start();
	            thread5.start();
	            
	            thread1.join();
	            thread2.join();
	            thread3.join();
	            thread4.join();
	            thread5.join();
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("",e);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("",e);
		}
		
	}
	
	private static final ThreadLocal<Socket> LOCAL = new ThreadLocal<Socket>();
	
	@Override
	public <T> T send(T msg) throws Exception {
		Socket socket = LOCAL.get();
	    if (socket == null || !socket.isConnected()) {
	    	logger.info("create new socket=========");
	    	socket = SocketFactory.getDefault().createSocket(host, port);
	        
	        LOCAL.set(socket);
	    }
	    
	    OutputStream outputStream = socket.getOutputStream();
	    InputStream inputStream = socket.getInputStream();
        
        ObjectEncoderOutputStream oout = new ObjectEncoderOutputStream(outputStream);
        oout.writeObject(msg);
        oout.flush();

        ObjectDecoderInputStream oinput = new ObjectDecoderInputStream(inputStream);
        Object readObject = oinput.readObject();
	    
        //oout.close();
        //oinput.close();
		
		return (T) readObject;
	}

}
