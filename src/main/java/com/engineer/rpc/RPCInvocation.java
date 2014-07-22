package com.engineer.rpc;

public interface RPCInvocation {
	/**
	 * @return the result
	 */
	public Object getResult();
	
	public void setResult(Object value);
	
	/**
	 * @return the interfaces
	 */
	public Class<?> getInterfaces();
	
	/**
	 * @return the method
	 */
	public String getMethod();
	
	/**
	 * @return the params
	 */
	public Object[] getParams();
	
	/**
	 * @return the Method parameter types
	 */
	public Class<?>[] getMethodParameterTypes();

	public void setParams(Object[] objects);
}
