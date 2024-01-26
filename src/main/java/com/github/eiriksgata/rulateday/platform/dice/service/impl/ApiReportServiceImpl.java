package com.github.eiriksgata.rulateday.platform.dice.service.impl;

import com.github.eiriksgata.rulateday.platform.dice.service.ApiReportService;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2021/6/7
 **/
@Service
public class ApiReportServiceImpl implements ApiReportService {

    //public static String apiUrl = ResourceBundle.getBundle("resources").getString("api.server.url");


    @Override
    public void exceptionReport(String title, String content, Long qqId) {


    }

}
