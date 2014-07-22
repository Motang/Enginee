package com.engineer.rpc.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
public class NettyClientChannelHandler extends ChannelInboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(NettyClientChannelHandler.class);
	
//	private static final ThreadLocal<NettyClientChannelHandler> LOCAL = new ThreadLocal<NettyClientChannelHandler>() {
//		@Override
//		protected NettyClientChannelHandler initialValue() {
//			return new NettyClientChannelHandler();
//		}
//	};

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	private int timeout = 30000; //three seconds
	

	private Map<String, Object> result = new HashMap<String, Object>();
	
	private Channel channel;
	

	public NettyClientChannelHandler() {
		super();
	}


	public NettyClientChannelHandler(int timeout) {
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
			lock.lock();
			result.put(getKey(invo), invo);
			
			condition.signal();
			//client.notified();
		} finally {
			lock.unlock();
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
		channel.writeAndFlush(msg);
		
		lock.lock();
		try {
			condition.await(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.error("",e);
		} finally {
			lock.unlock();
		}
		return (T) getResult((RPCInvocation) msg);
	}
}
