package com.kk.rpc.server;

public class TestServer {
    public static void main(String[] args) {
        new Server(10010).listen();
    }
}
