package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock;

import lombok.Data;

@Data
public class WsRequestBean<T> {

    private String action;
    private String echo;
    private T params;


}
