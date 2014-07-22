package com.engineer.rpc.bio;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.engineer.rpc.RPCHello;
import com.engineer.rpc.RPCInvocation;
import com.engineer.rpc.RPCServer;
import com.engineer.rpc.impl.RPCHelloImpl;

public class BIOServer implements RPCServer {
	private int port;
	private boolean isRuning = true;
	private BIOListener listener;
	
	private Map<String, Object> serviceEngine = new HashMap<String, Object>();

	public BIOServer() {
		super();
	}

	public BIOServer(int port) {
		super();
		this.port = port;
	}

	public void call(RPCInvocation invo) {
		//System.out.println(invo.getClass().getName());
		Object obj = serviceEngine.get(invo.getInterfaces().getName());
		if (obj != null) {
			try {
				Method m = obj.getClass().getMethod(invo.getMethod(),
						invo.getMethodParameterTypes());
				Object result = m.invoke(obj, invo.getParams());
				invo.setResult(result);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("has no these class");
		}
	}

	public void register(Class<?> interfaceDefiner, Class<?> impl) {
		try {
			this.serviceEngine.put(interfaceDefiner.getName(), impl.newInstance());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
		System.out.println("启动服务器");
		listener = new BIOListener(this);
		this.isRuning = true;
		listener.start();
	}

	public void stop() {
		this.isRuning = false;
	}

	public boolean isRunning() {
		return isRuning;
	}

	@Override
	public void setPort(int port) {
		this.port=port;
	}
	
	public int getPort(){
		return port;
	}

	public static void main(String[] args) {
		BIOServer server = new BIOServer(8099);
		server.register(RPCHello.class, RPCHelloImpl.class);
		server.start();
	}
}
