package com.engineer.rpc.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engineer.rpc.RPCInvocation;

@Sharable
public class NettyClientChannelHandler2 extends ChannelInboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(NettyClientChannelHandler2.class);
	
//	private static final ThreadLocal<NettyClientChannelHandler> LOCAL = new ThreadLocal<NettyClientChannelHandler>() {
//		@Override
//		protected NettyClientChannelHandler initialValue() {
//			return new NettyClientChannelHandler();
//		}
//	};

	private int timeout = 30000; //three seconds
	

	private Map<String, Object> result = new HashMap<String, Object>();
	private ChannelPromise promise;
	
	private Channel channel;
	

	public NettyClientChannelHandler2() {
		super();
	}


	public NettyClientChannelHandler2(int timeout) {
		super();
		this.timeout = timeout;
	}


	@Override
	public void channelRegistered(ChannelHandlerContext ctx) {
		channel = ctx.channel();
		//LOCAL.set(this);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			RPCInvocation invo = (RPCInvocation) msg;
			logger.debug("Read {}", invo.getResult());
			result.put(getKey(invo), invo);
			
			promise.setSuccess();
			//client.notified();
		} finally {
			ReferenceCountUtil.release(msg);
			
		}

	}

	private String getKey(RPCInvocation invo) {
		StringBuilder sb = new StringBuilder();
		sb.append(invo.getInterfaces().getName());
		sb.append("-").append(invo.getMethod());
		sb.append("-").append(StringUtils.toString(invo.getMethodParameterTypes()));
		sb.append("-").append(StringUtils.toString(invo.getParams()));
		return sb.toString();
	}

	public Object getResult(RPCInvocation invo) {
		return result.get(getKey(invo));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.debug("Unregistered {}", "=====");
		ctx.fireChannelUnregistered();
	}
	
	public <T> T send(T msg) {
		logger.debug("send {}", msg);
		promise = channel.newPromise();
		
		channel.writeAndFlush(msg);
		
		try {
			promise.await(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (T) getResult((RPCInvocation) msg);
	}
}
