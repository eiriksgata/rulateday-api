package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock;

import lombok.Data;

@Data
public class WsResponseBean<T> {

    private String status;
    private Integer retcode;
    private String msg;

    private String wording;
    private T data;
    private String echo;


}
