package com.github.eiriksgata.rulateday.platform.dice.service.impl;

import com.github.eiriksgata.rulateday.platform.dice.service.UserTempDataService;
import com.github.eiriksgata.rulateday.platform.mapper.UserTempDataMapper;
import com.github.eiriksgata.rulateday.platform.pojo.UserTempData;
import com.github.eiriksgata.trpg.dice.config.DiceConfig;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTempDataServiceImpl implements UserTempDataService {

    @Autowired
    UserTempDataMapper userTempDataMapper;

    @Override
    public void updateUserAttribute(Long id, String attribute) {
        UserTempData userTempData = null;
        try {
            userTempData = userTempDataMapper.selectById(id);
        } catch (PersistenceException e) {
            userTempDataMapper.createTable();
        }
        if (userTempData == null) {
            addUserTempData(id);
        }
        userTempDataMapper.updateAttributeById(id, attribute);
    }

    @Override
    public void updateUserDiceFace(Long id, int diceFace) {
        UserTempData userTempData = null;
        try {
            userTempData = userTempDataMapper.selectById(id);
        } catch (PersistenceException e) {
            userTempDataMapper.createTable();
        }
        if (userTempData == null) {
            addUserTempData(id);
        }
        userTempDataMapper.updateDiceFaceById(id, diceFace);
    }


    @Override
    public void addUserTempData(Long id) {
        UserTempData userTempData = new UserTempData();
        userTempData.setId(id);
        userTempData.setAttribute("");
        userTempData.setDice_face(Integer.valueOf(
                DiceConfig.diceSet.getString(
                        DiceConfig.diceSet.getString("dice.type") + ".face")
        ));
        userTempDataMapper.insert(userTempData);
    }

    @Override
    public Integer getUserDiceFace(Long id) {
        try {
            return userTempDataMapper.selectById(id).getDice_face();
        } catch (NullPointerException ignored) {
            return null;
        } catch (PersistenceException e) {
            userTempDataMapper.createTable();
            return null;
        }
    }

    @Override
    public String getUserAttribute(Long id) {
        try {
            return userTempDataMapper.selectById(id).getAttribute();
        } catch (NullPointerException ignored) {
            return null;
        } catch (PersistenceException e) {
            userTempDataMapper.createTable();
            return null;
        }
    }

}
