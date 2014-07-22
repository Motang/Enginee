package com.engineer.webservice;

import java.util.Map.Entry;
import java.util.Set;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;

public class TraceHandler implements Handler,LogicalHandler, SOAPHandler{

	@Override
	public void close(MessageContext arg0) {
		System.out.println("close.....");
		Set<Entry<String, Object>> entrySet = arg0.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			System.out.println("Key=" + entry.getKey() + ", Value=" + entry.getValue());
		}
	}

	@Override
	public boolean handleFault(MessageContext arg0) {
		System.out.println("Handle fault.....");
		Set<Entry<String, Object>> entrySet = arg0.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			System.out.println("Key=" + entry.getKey() + ", Value=" + entry.getValue());
		}
		return false;
	}

	@Override
	public boolean handleMessage(MessageContext arg0) {
		System.out.println("Enter.....");
		Set<Entry<String, Object>> entrySet = arg0.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			System.out.println("Key=" + entry.getKey() + ", Value=" + entry.getValue());
		}
		return true;
	}

	@Override
	public Set getHeaders() {
		return null;
	}

}
