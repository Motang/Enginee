package com.engineer.rpc.netty;

import java.io.Serializable;
import java.util.Arrays;

import com.engineer.rpc.RPCInvocation;

public class NettyInvocation implements RPCInvocation,Serializable{

	private static final long serialVersionUID = 1L;
	
	private Class<?> interfaces;
	private String method;
	private Class<?> methodParameterTypes[];
	private Object[] params;
	private Object result;
	
	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}
	/**
	 * @return the interfaces
	 */
	public Class<?> getInterfaces() {
		return interfaces;
	}
	/**
	 * @param interfaces the interfaces to set
	 */
	public void setInterfaces(Class<?> interfaces) {
		this.interfaces = interfaces;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}
	public Class<?>[] getMethodParameterTypes() {
		return methodParameterTypes;
	}
	public void setMethodParameterTypes(Class<?>[] methodParameterTypes) {
		this.methodParameterTypes = methodParameterTypes;
	}
	@Override
	public String toString() {
		return interfaces.getName()+"."+method+"("+Arrays.toString(params)+")";
	}

}
