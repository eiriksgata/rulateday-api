package com.github.eiriksgata.rulateday.platform.dice.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.instruction.QueryInstruct;
import com.github.eiriksgata.rulateday.platform.dice.service.UserConversationService;
import com.github.eiriksgata.rulateday.platform.mapper.UserConversationMapper;
import com.github.eiriksgata.rulateday.platform.pojo.QueryDataBase;
import com.github.eiriksgata.rulateday.platform.pojo.UserConversation;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2021/2/20
 **/
@Service
public class UserConversationServiceImpl implements UserConversationService {

    @Autowired
    UserConversationMapper userConversationMapper;

    @Override
    public void saveConversation(Long qq, List<QueryDataBase> queryData) {
        UserConversation userConversation = new UserConversation();
        userConversation.setId(qq);
        userConversation.setData(JSONObject.toJSONString(queryData));
        userConversation.setTimestamp(System.currentTimeMillis());
        try {
            if (userConversationMapper.selectById(qq) == null) {
                userConversationMapper.insert(userConversation);
            } else {
                userConversationMapper.updateDataById(userConversation);
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            try {
                userConversationMapper.createTable();
                userConversationMapper.insert(userConversation);
            } catch (PersistenceException ignored) {
            }
        }
    }

    @Override
    public String checkInputQuery(DiceMessageDTO data) {
        //检测用户是否有对话模式记录
        UserConversation result = userConversationMapper.selectById(data.getSanderId());
        if (result == null) {
            return null;
        }
        if (System.currentTimeMillis() - result.getTimestamp() > 1000 * 2 * 60) {
            userConversationMapper.deleteById(data.getSanderId());
            return null;
        }

        try {
            int number = Integer.parseInt(data.getBody());
            List<QueryDataBase> queryData = JSONObject.parseObject(result.getData(), new TypeReference<List<QueryDataBase>>() {
            }.getType());

            if (number >= queryData.size() || number < 0) {
                return "输入的数字需要在0-" + queryData.size() + "范围内";
            }
            userConversationMapper.deleteById(data.getSanderId());
            if (queryData.get(number).getName().length() > 5) {
                if (queryData.get(number).getName().startsWith("怪物图鉴:")) {
                    QueryInstruct.cachedThread.execute(() -> new Dnd5eLibServiceImpl().
                            sendMMImage(data, queryData.get(number)));
                }
            }
            return queryData.get(number).getDescribe();
        } catch (Exception e) {
            userConversationMapper.deleteById(data.getSanderId());
            return "输入的数字不符合要求，已取消对话模式";
        }
    }

}
