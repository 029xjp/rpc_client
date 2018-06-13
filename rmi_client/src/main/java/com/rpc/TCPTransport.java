package com.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPTransport {
    private String serviceAddress;

    public TCPTransport(String serviceAddress) {
        this.serviceAddress=serviceAddress;
    }

    private Socket newSocket(){
        System.out.println("创建一个新的连接");
        Socket socket = null;
        try {
            String[] arrs=serviceAddress.split(":");
            socket = new Socket(arrs[0],Integer.parseInt(arrs[1]));
        } catch (Exception e) {
            throw  new RuntimeException("连接建立失败");
        }
        return socket;
    }

    public Object send(RpcRequest rpcRequest){
        Socket socket = null;
        try {
            socket = newSocket();
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(rpcRequest);
            os.flush();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object result = ois.readObject();

            ois.close();
            os.close();
            return result;
        } catch (Exception e) {
            throw  new RuntimeException("发起远程调用异常",e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
