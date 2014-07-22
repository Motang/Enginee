package com.engineer.rpc.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import com.engineer.rpc.RPCClient;
import com.engineer.rpc.RPCHello;

public class BIOClient implements RPCClient{
	private String host;  
    private int port;
    
    private Socket socket;  
    
	public BIOClient(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}
	
	public void start() {
		try {
			socket = SocketFactory.getDefault().createSocket(host, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static <T> T getProxy(final Class<T> clazz, String host, int port) {

		final BIOClient client = new BIOClient(host, port);
		client.start();
		
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				BIOInvocation invo = new BIOInvocation();
				invo.setInterfaces(clazz);
				invo.setMethod(method.getName());
				invo.setMethodParameterTypes(method.getParameterTypes());
				invo.setParams(args);
				
				BIOInvocation result = client.send(invo);
				
				return result.getResult();
			}
		};
		
		@SuppressWarnings("unchecked")
		T t = (T) Proxy.newProxyInstance(BIOClient.class.getClassLoader(), new Class<?>[] { clazz }, handler);
		return t;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			RPCHello hello = BIOClient.getProxy(RPCHello.class, "localhost", 8099);
			String name = "Morly" + i;
			String sayHello = hello.sayHello(name);
			System.out.println(sayHello);
		}
	}

	@Override
	public void close() throws IOException {
		if (socket.isConnected() && !socket.isClosed()) {
			socket.close();
		}
		
	}

	@Override
	public <T> T send(T msg) throws Exception {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(msg);
			oos.flush();  
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());  
			T result = (T) ois.readObject();  
			return result;
		} finally {
			socket.close();
		}
	}
}
