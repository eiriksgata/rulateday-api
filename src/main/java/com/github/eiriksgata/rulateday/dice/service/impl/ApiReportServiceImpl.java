package com.github.eiriksgata.rulateday.dice.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.github.eiriksgata.rulateday.dice.service.ApiReportService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2021/6/7
 **/
@Service
public class ApiReportServiceImpl implements ApiReportService {

    public static String apiUrl = ResourceBundle.getBundle("resources").getString("api.server.url");

    @Override
    public void exceptionReport(String title, String content, Long qqId) {


    }

}
