package com.kk.rpc.server.service.impl;

import com.kk.rpc.api.biz.service.HelloService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImpl implements HelloService {
    public void say() {
        log.info("hello");
    }

    public String getResult(String msg) {
        log.info("hello,you will get result");
        return new StringBuffer("服务端接收到信息:").append(msg).toString();
    }
}
