package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Data;

@Data
public class UserPasswordResetVo {

    private String oldPassword;
    private String newPassword;

}
