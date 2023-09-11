package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Data;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi.vo
 * date: 2021/5/20
 **/

@Data
public class PageHelperBean<T> {

    private int pageNumber;
    private int pageSize;
    private T data;

}
