package com.engineer.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.engineer.rpc.RPCHello;
import com.engineer.rpc.RPCInvocation;
import com.engineer.rpc.RPCServer;
import com.engineer.rpc.impl.RPCHelloImpl;

public class NettyServer implements RPCServer{
	private int port = 8099;
	private boolean isRuning = false;

	private Map<String, Object> serviceEngine = new HashMap<String, Object>();
	
	public NettyServer(int port) {
		super();
		this.port = port;
	}

	@Override
	public void register(Class<?> interfaceDefiner, Class<?> impl) {
		try {
			this.serviceEngine.put(interfaceDefiner.getName(), impl.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap server = new ServerBootstrap();
			
			server.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new LoggingHandler(LogLevel.DEBUG))
			.childHandler(new ChannelInitializer<NioSocketChannel>() {
				@Override
				protected void initChannel(NioSocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(
							new ObjectEncoder(),
	                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
							new NettyServerChannelHandler(NettyServer.this));
					
				}
			});
			
			ChannelFuture f = server.bind(port).sync();
			
			ChannelFuture closeFuture = f.channel().closeFuture();
			closeFuture.addListener(ChannelFutureListener.CLOSE);
			closeFuture.sync();
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        // Shut down all event loops to terminate all threads.
	        bossGroup.shutdownGracefully();
	        workerGroup.shutdownGracefully();
	    }
		
	}

	@Override
	public void stop() {
		isRuning = false;
	}

	@Override
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

	@Override
	public void call(RPCInvocation invo) {
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

	public static void main(String[] args) {
		NettyServer server = new NettyServer(8099);
		server.register(RPCHello.class, RPCHelloImpl.class);
		server.start();
	}
}
