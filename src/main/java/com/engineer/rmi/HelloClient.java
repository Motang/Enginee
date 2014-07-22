package com.engineer.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloClient {
	public static void main(String[] args) {
		try {
			//Hello hello = (Hello) Naming.lookup("Hello");
			//如果要从另一台启动了RMI注册服务的机器上查找hello实例     
			Hello hello = (Hello)Naming.lookup("//10.141.139.38:8089/Hello");     
			
			//调用远程方法     
			System.out.println(hello.say());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        
	}
}
