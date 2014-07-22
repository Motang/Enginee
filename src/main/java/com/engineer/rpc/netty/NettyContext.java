package com.engineer.rpc.netty;


public class NettyContext {
	private static final ThreadLocal<NettyContext> LOCAL = new ThreadLocal<NettyContext>() {
		@Override
		protected NettyContext initialValue() {
			return new NettyContext();
		}
	};
	
	public static NettyContext get() {
		return LOCAL.get();
	}
}
