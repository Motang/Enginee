package com.engineer.mina.chat;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaChatServer implements IoHandler{
	static {
		System.setProperty("log4j.configuration", "log4j_test.properties");
	}
	
	private final static Logger logger = LoggerFactory.getLogger(MinaChatServer.class);
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
		
		filterChain.addLast("log", new LoggingFilter());
		filterChain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		
		acceptor.setHandler(new MinaChatServer());
        acceptor.bind(new InetSocketAddress(1234));

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
		session.write("Welcome " + msg);
	}

	@Override
	public void messageSent(IoSession session, Object msg) throws Exception {
		logger.debug("Sent something... {}", msg);
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.debug("Close Session.");
		session.close(true);
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
