package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Data;

@Data
public class LoginVo<T> {

    private String username;
    private String password;

    private T data;
    private long timestamp;
}
