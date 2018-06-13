package com.rpc;

import com.rpc.zookeeper.IServiceDiscover;

import java.lang.reflect.Proxy;

public class RpcClientProxy {
    private IServiceDiscover serviceDiscovery;

    public RpcClientProxy(IServiceDiscover serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }
    public <T> T clientProxy(final Class<T> interfaces,final String version){

        return (T)Proxy.newProxyInstance(interfaces.getClassLoader(),new Class[]{interfaces},
                new RemoteInvocationHanlder(serviceDiscovery,version));

    }
}
