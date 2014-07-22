package com.engineer.rpc.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engineer.rpc.RPCInvocation;
import com.engineer.rpc.RPCServer;

@Sharable
public class NettyServerChannelHandler extends ChannelInboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(NettyServerChannelHandler.class);
	
	private RPCServer server;

	public NettyServerChannelHandler(RPCServer server) {
		super();
		this.server = server;
	}
	

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	RPCInvocation invo = (RPCInvocation) msg;
    	server.call(invo);
    	
    	Object[] params = invo.getParams();
    	
    	ChannelFuture future = ctx.writeAndFlush(invo);
    	try {
    		future.sync();
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
		if (params != null && "DONE".equals(params[0])) {
			ChannelFuture closeFuture = ctx.channel().closeFuture();
			closeFuture.addListener(ChannelFutureListener.CLOSE);
			ctx.close();
		}
        logger.debug("Server write {}",invo);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //ChannelHandlerContext flush = ctx.flush();
        //flush.
    	
    	//ctx.flush();
    }
    
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    	logger.debug("Server Unregistered {}",">>>>");
        ctx.fireChannelUnregistered();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	logger.error("",cause);
        ctx.close();
    }

}
