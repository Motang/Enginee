package com.engineer.rpc.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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
	

	private Map<String, Object> result = new ConcurrentHashMap<String, Object>();
	
	private Map<String, ChannelPromise> promiseMaps = new ConcurrentHashMap<String, ChannelPromise>();
	
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
			result.put(invo.getId(), invo);
			
			ChannelPromise channelPromise = promiseMaps.get(invo.getId());
			channelPromise.setSuccess();
			
		} finally {
			ReferenceCountUtil.release(msg);
			
		}

	}

	public Object getResult(RPCInvocation invo) {
		return result.get(invo.getId());
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
		final RPCInvocation request = (RPCInvocation) msg;
		
		ChannelPromise promise = channel.newPromise();
		promise.addListener(new FutureListener<Object>() {
			@Override
			public void operationComplete(Future<Object> future) throws Exception {
				promiseMaps.remove(request.getId());
				
			}
		});
		
		promiseMaps.put(request.getId(), promise);
		
		channel.writeAndFlush(msg);
		
		try {
			promise.await(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return (T) getResult(request);
	}
}
