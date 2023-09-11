package com.github.eiriksgata.rulateday.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.service.RandomPictureService;
import com.github.eiriksgata.rulateday.platform.service.SystemSetService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.eiriksgata.rulateday.platform.utils.FileUtil;
import com.github.eiriksgata.rulateday.platform.utils.HexConvertUtil;
import com.github.eiriksgata.rulateday.platform.utils.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class RandomPictureServiceImpl implements RandomPictureService {

    @Autowired
    SystemSetService systemSetService;



}
