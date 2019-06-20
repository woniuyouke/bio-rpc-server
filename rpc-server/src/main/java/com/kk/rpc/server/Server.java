package com.kk.rpc.server;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server {

    private Integer port;

    public Server(Integer port){
        this.port = port;
    }

    public void  listen(){
        log.info("服务端开启监听，监听端口={}",port);
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            while (true) {
                Socket client = socket.accept();

                new Thread(new HandleSocket(client)).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket != null && !socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
