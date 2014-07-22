package com.engineer.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
	public String say() throws RemoteException;
}
