package com.kk.rpc.api.channel;

import lombok.Data;

import java.io.Serializable;


@Data
public class KRequest implements Serializable {

    private String clazzName;

    private Object[] params;

    private String methodName;

}
