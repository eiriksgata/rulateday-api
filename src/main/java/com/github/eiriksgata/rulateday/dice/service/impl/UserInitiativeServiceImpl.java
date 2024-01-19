package com.github.eiriksgata.rulateday.dice.service.impl;

import com.github.eiriksgata.rulateday.dice.service.UserInitiativeService;
import com.github.eiriksgata.rulateday.platform.mapper.UserInitiativeDataMapper;
import com.github.eiriksgata.rulateday.platform.pojo.UserInitiativeData;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2021/3/11
 **/
@Service
public class UserInitiativeServiceImpl implements UserInitiativeService {

    @Autowired
    UserInitiativeDataMapper userInitiativeDataMapper;

    @Override
    public boolean diceLimit(String groupId) {
        List<UserInitiativeData> initiativeDataList = userInitiativeDataMapper.selectByGroupId(groupId);
        return initiativeDataList.size() > 30;
    }

    @Override
    public void addInitiativeDice(String groupId, Long userId, String name, int value) {
        UserInitiativeData result = userInitiativeDataMapper.selectByGroupIdAndUserIdAndName(groupId, userId, name);
        UserInitiativeData userInitiativeData = new UserInitiativeData();
        userInitiativeData.setGroupId(groupId);
        userInitiativeData.setName(name);
        userInitiativeData.setUserId(userId);
        userInitiativeData.setValue(value);
        try {
            if (result != null) {
                userInitiativeDataMapper.deleteById(result.getId());
            }
            userInitiativeDataMapper.insert(userInitiativeData);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDice(String groupId, Long userId, String name) {
        userInitiativeDataMapper.delete(groupId, userId, name);
    }

    @Override
    public void clearGroupDice(String groupId) {
        userInitiativeDataMapper.deleteByGroupId(groupId);
    }

    @Override
    public String showInitiativeList(String groupId) {
        List<UserInitiativeData> initiativeDataList = userInitiativeDataMapper.selectByGroupId(groupId);
        if (initiativeDataList.size() == 0) {
            return CustomText.getText("initiative.null");
        }
        initiativeSort(initiativeDataList);
        StringBuilder resultText = new StringBuilder();
        for (int i = 0; i < initiativeDataList.size(); i++) {
            resultText
                    .append("\n")
                    .append(i + 1 + ". ")
                    .append(initiativeDataList.get(i).getName())
                    .append("[")
                    .append(initiativeDataList.get(i).getValue())
                    .append("]");

        }
        return CustomText.getText("initiative.show", resultText.toString());
    }

    private void initiativeSort(List<UserInitiativeData> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(i).getValue() > list.get(j).getValue()) {
                    UserInitiativeData temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }


}
