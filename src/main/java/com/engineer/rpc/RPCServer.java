package com.engineer.rpc;


public interface RPCServer {
	public void start();
	public void stop();
	public boolean isRunning();
	
	public void register(Class<?> interfaceDefiner, Class<?> implClass);
	public void setPort(int port);
	public int getPort();
	
	public void call(RPCInvocation invo);
}
