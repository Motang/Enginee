package com.engineer.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engineer.rpc.RPCClient;
import com.engineer.rpc.RPCHello;

public class NettyClient implements RPCClient {
	static Logger logger = LoggerFactory.getLogger(NettyClient.class);
	
	private String host;  
    private int port;
    private int timeout = 30000; //three seconds
    
    private Channel clientChannel;
    private EventLoopGroup group = new NioEventLoopGroup();
    
	public NettyClient(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	@Override
	public void start() {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<NioSocketChannel>() {
                 @Override
                 public void initChannel(NioSocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
					p.addLast(
                    		 new ObjectEncoder(),
                             new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                             new NettyClientChannelHandler(timeout));
                 }
             });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();
            
            // Wait until the connection is closed.
            clientChannel = f.channel();
			//clientChannel.closeFuture().sync();
            ChannelFuture closeFuture = clientChannel.closeFuture();
			closeFuture.addListener(ChannelFutureListener.CLOSE);
			//closeFuture.sync();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            // Shut down the event loop to terminate all threads.
            //group.shutdownGracefully();
        }
	}
	
	public void close() throws IOException {
		group.shutdownGracefully();
	}
	
	
	
	public static void main(String[] args) {
		NettyClient client = new NettyClient("localhost",8099);
		client.start();
		
		try {
			RPCHello proxy = NettyClientHelper.getProxy(client, RPCHello.class);
			for (int i = 0; i < 1000; i++) {
				System.err.println(proxy.sayHello("Morly"+i));
					TimeUnit.SECONDS.sleep(1);
			}
			System.err.println(proxy.sayHello("DONE"));
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("",e);
		}
		
		//TimeUnit.SECONDS.sleep(1);
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//proxy.sayHello("Done agagin");
	}
	
	@Override
	public <T> T send(T msg) throws Exception {
		NettyClientChannelHandler handler = clientChannel.pipeline().get(NettyClientChannelHandler.class);
		T send = handler.send(msg);
		
		return send;
	}

}
