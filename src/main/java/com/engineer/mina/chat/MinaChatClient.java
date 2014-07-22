package com.engineer.mina.chat;

import java.net.InetSocketAddress;
import java.util.Scanner;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaChatClient implements IoHandler {
	static {
		System.setProperty("log4j.configuration", "log4j_test.properties");
	}
	
	private final static Logger logger = LoggerFactory.getLogger(MinaChatClient.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NioSocketConnector connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
		filterChain.addLast("logger", new LoggingFilter());
		filterChain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		connector.setHandler(new MinaChatClient());
        ConnectFuture future = connector.connect(new InetSocketAddress(1234));
        future.awaitUninterruptibly();
        if (!future.isConnected()) {
        	logger.debug("Cannot connect....");
        }
        IoSession session = future.getSession();
        Scanner scanner = new Scanner(System.in);
        logger.info("Please type your message here...");
        String message = scanner.nextLine(); 
        session.write(message);
        logger.info("Over....");
        
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.warn("Unexpected exception.", cause);
        // Close connection when unexpected exception is caught.
        session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object msg) throws Exception {
		logger.debug("Receive something... {}", msg);
		session.close(true);
	}

	@Override
	public void messageSent(IoSession session, Object msg) throws Exception {
		logger.debug("Sent something... {}", msg);
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.debug("Close Session.");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.debug("Create a session.");
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.debug("Session Idel with IdleStatus={}.", status);
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("Session is Open.");
		
	}

}
