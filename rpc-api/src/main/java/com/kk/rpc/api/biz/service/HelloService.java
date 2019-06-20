package com.kk.rpc.api.biz.service;

import com.kk.rpc.api.annotation.KInvoker;

@KInvoker("com.kk.rpc.server.service.impl.HelloServiceImpl")
public interface HelloService {

    void say();

    String getResult(String msg);
}
