package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Data;

@Data
public class LoginVo {

    private String username;
    private String password;

    private String captcha;
    private long timestamp;
}
