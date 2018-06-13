package com.rpc;

import com.rpc.zookeeper.IServiceDiscover;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHanlder implements InvocationHandler {
    private IServiceDiscover serviceDiscovery;

    private String version;

    public RemoteInvocationHanlder(IServiceDiscover serviceDiscovery,String version) {
        this.serviceDiscovery=serviceDiscovery;
        this.version=version;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParams(args);
        rpcRequest.setVersion(version);
        String serviceAddress=serviceDiscovery.discover(rpcRequest.getClassName());
        TCPTransport transport = new TCPTransport(serviceAddress);
        Object result = transport.send(rpcRequest);
        return result;
    }
}
