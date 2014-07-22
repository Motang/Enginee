package com.engineer.rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.engineer.rmi.impl.HelloImpl;

public class HelloServer {
	public static void main(String[] args) {
        try {
        	//启动RMI注册服务，指定端口为1099　（1099为默认端口）     
        	//也可以通过命令 ＄java_home/bin/rmiregistry 1099启动     
        	//这里用这种方式避免了再打开一个DOS窗口     
        	//而且用命令rmiregistry启动注册服务还必须事先用RMIC生成一个stub类为它所用     
        	LocateRegistry.createRegistry(8089);
        	//Runtime.getRuntime().exec("rmiregistry 2020");
	        //创建远程对象的一个或多个实例，下面是hello对象     
	        //可以用不同名字注册不同的实例
	        Hello hello = new HelloImpl("Hello, world!");     
	            
	        //把hello注册到RMI注册服务器上，命名为Hello     
	        //Naming.rebind("Hello", hello);
	        Naming.rebind("//10.141.139.38:8089/Hello", hello);     
	             
	        //如果要把hello实例注册到另一台启动了RMI注册服务的机器上     
	        //Naming.rebind("//192.168.1.105:1099/Hello",hello);     
	            
	        System.out.println("Hello Server is ready.");  
        } catch (RemoteException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        
	}
}
