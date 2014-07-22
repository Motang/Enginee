package com.engineer.webservice;

import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

public class StartServer {
	public static void main(String[] args) {
		
		/*
		 * 生成Example 服务实例
		 */
		Example serverBean = new Example();
		System.out.println(System.getProperty("java.endorsed.dirs"));
		/*
		 * 发布Web Service到http://localhost:8080/hello地址
		 */
		Endpoint endpoint = Endpoint.publish("http://localhost:8080/hello", serverBean);
		Binding binding = endpoint.getBinding();
		/*
		 * 设置一个SOAP协议处理栈 这里就简单得打印SOAP的消息文本
		 */
		List<Handler> handlerChain = new LinkedList<Handler>();
		handlerChain.add(new TraceHandler());
		binding.setHandlerChain(handlerChain);
		System.out.println("服务已启动 http://localhost:8080/hello");
	}
}
