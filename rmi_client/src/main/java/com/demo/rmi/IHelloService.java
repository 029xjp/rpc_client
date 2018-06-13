package com.demo.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHelloService extends Remote {
    public String sayHello(String message) throws RemoteException;
}
