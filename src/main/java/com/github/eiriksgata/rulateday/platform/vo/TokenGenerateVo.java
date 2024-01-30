package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TokenGenerateVo {

    private Date issuedAt;
    private Date expireDate;
}
