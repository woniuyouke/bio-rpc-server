package com.kk.rpc.server;

import com.kk.rpc.api.channel.KRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

@Slf4j
public class HandleSocket implements  Runnable{

    private  Socket socket;
    public HandleSocket(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        log.info("接收到连接");
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            KRequest kRequest = (KRequest) objectInputStream.readObject();
            if(kRequest == null){
                throw  new RuntimeException("不是rpc的调用方式,不进行处理");
            }
            //用反射得到类型
            Class clazz = Class.forName(kRequest.getClazzName());
            Class[] paramTypes = null;
            Object[] params = kRequest.getParams();
            //获取入参类型
            if(params != null && params.length > 0){
                paramTypes = new Class[kRequest.getParams().length];
                for(int i=0;i<params.length;i++){
                    paramTypes[i] = params[i].getClass();
                }
            }
            log.info("接收到的信息={}",params);
            log.info("需要进行处理的方法={}",kRequest.getMethodName());
            Method method = clazz.getDeclaredMethod(kRequest.getMethodName(),paramTypes);
            Object result = method.invoke(clazz.newInstance(),params);
            log.info("处理后的结果={}",result);
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
            log.error("出现异常",e);
        }finally {
            log.info("关闭流和连接");
            if(objectInputStream != null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectOutputStream != null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(!socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("连接处理完毕");
    }
}
