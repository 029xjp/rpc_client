package com.rpc;

import com.rpc.zookeeper.IServiceDiscover;
import com.rpc.zookeeper.ServiceDiscoverImpl;
import com.rpc.zookeeper.ZkConfig;

public class ClientDemo {
    public static void main(String[] args) {
        IServiceDiscover serviceDiscovery=new
                ServiceDiscoverImpl(ZkConfig.CONNECT_STR);

        RpcClientProxy rpcClientProxy=new RpcClientProxy(serviceDiscovery);

        for(int i=0;i<10;i++) {
            ISayHiService sayHiService = rpcClientProxy.clientProxy(ISayHiService.class, null);
            System.out.println(sayHiService.sayHello("xjp"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
