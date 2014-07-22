package com.engineer.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.engineer.rmi.Hello;

public class HelloImpl extends UnicastRemoteObject implements Hello {
	
	private String message; 
    public HelloImpl(String msg) throws RemoteException {     
       message = msg;     
    }     
     
    @Override
    public String say()throws RemoteException{ 
      System.out.println("Called by HelloClient"); 
      return message; 
    } 

}
