package com.github.eiriksgata.rulateday.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.mapper.UserMapper;
import com.github.eiriksgata.rulateday.platform.service.RandomPictureService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulatedayapi.controller
 * date: 2021/6/8
 **/
@RestController
public class CollectionController {

    @Autowired
    RandomPictureService randomPictureService;

    @Autowired
    UserMapper userMapper;


    @GetMapping("/picture/random")
    public ResponseBean<?> pictureRandom() {
        return ResponseBean.error("没有实现");
    }


    @PutMapping("/user/info")
    public ResponseBean<?> userInfo(@RequestBody JSONObject jsonObject) {
        return ResponseBean.success(
                userMapper.selectUrp(jsonObject.getLong("id"))
        );
    }


}
