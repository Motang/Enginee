package com.engineer.rpc.netty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import com.engineer.rpc.RPCClient;
import com.engineer.rpc.impl.RPCInvocationImpl;

public class NettyClientHelper {
	
	public static <T> T getProxy(final RPCClient client, final Class<T> clazz) {

		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				RPCInvocationImpl invo = new RPCInvocationImpl();
				invo.setId(UUID.randomUUID().toString());
				invo.setInterfaces(clazz);
				invo.setMethod(method.getName());
				invo.setMethodParameterTypes(method.getParameterTypes());
				invo.setParams(args);
				
				RPCInvocationImpl result = client.send(invo);
				
				//future.addListener(ChannelFutureListener.CLOSE);
				return result.getResult();
			}
		};
		
		@SuppressWarnings("unchecked")
		T t = (T) Proxy.newProxyInstance(NettyClientHelper.class.getClassLoader(), new Class<?>[] { clazz }, handler);
		return t;
	}
}
