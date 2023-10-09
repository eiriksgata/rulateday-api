package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Data;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulatedayapi.vo
 * date: 2021/5/20
 **/
@Data
public class DiceExceptionVo {
    private String title;
    private String content;
    private Long qqId = -1L;
}
