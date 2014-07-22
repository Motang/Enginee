package com.engineer.rpc.impl;

import com.engineer.rpc.RPCHello;

public class RPCHelloImpl implements RPCHello{

	@Override
	public String sayHello(String name) {
		System.out.println("GET "+ name);
		String result = "I am Server, Hello " + name;
		return result;
	}

}
