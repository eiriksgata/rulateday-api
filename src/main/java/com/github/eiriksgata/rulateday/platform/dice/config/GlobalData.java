package com.github.eiriksgata.rulateday.platform.dice.config;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.dice.dto.GroupRecordDTO;

import java.util.HashMap;
import java.util.Map;

public class GlobalData {

    public static JSONObject configData;

    public static Map<String, Map<String, String>> documentContext;

    public static Map<String, Long> groupChatRecordEnableMap = new HashMap<>();
    public static Map<String, GroupRecordDTO> groupChatRecordDataMap = new HashMap<>();

    public static int randomPictureApiType = 1;

}
