package com.github.eiriksgata.rulateday.platform.dice.service.impl;


import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.dice.init.CacheReuseData;
import com.github.eiriksgata.rulateday.platform.dice.service.BotControlService;
import com.github.eiriksgata.rulateday.platform.mapper.SpeakersGroupListMapper;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.trpg.dice.reply.CustomText;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service.impl
 * date: 2020/11/13
 **/
@Service
public class BotControlServiceImpl implements BotControlService {

    @Autowired
    SpeakersGroupListMapper speakersGroupListMapper;

    @Autowired
    ShamrockService shamrockService;

    @Override
    public boolean groupIsEnable(long groupId) {
        Boolean isEnable = speakersGroupListMapper.selectByGroupId(groupId);
        return isEnable == null || isEnable;
    }

    @Override
    public boolean groupIsBlacklist(long groupId) {
        try {
            Boolean isBlacklist = speakersGroupListMapper.selectBlacklistByGroupId(groupId);
            return Objects.requireNonNullElse(isBlacklist, false);
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public void groupEnable(long groupId) {
        try {
            if (speakersGroupListMapper.selectByGroupId(groupId) == null) {
                speakersGroupListMapper.insert(groupId, true, false);
            } else {
                speakersGroupListMapper.updateIsEnableById(groupId, true);
            }
        } catch (PersistenceException e) {
            speakersGroupListMapper.createTable();
        }
    }

    @Override
    public void groupDisable(long groupId) {
        try {
            if (speakersGroupListMapper.selectByGroupId(groupId) == null) {
                speakersGroupListMapper.insert(groupId, false, false);
            } else {
                speakersGroupListMapper.updateIsEnableById(groupId, false);
            }
        } catch (PersistenceException e) {
            speakersGroupListMapper.createTable();
        }
    }


    @Override
    public void groupBlacklistEnable(long groupId) {
        try {
            if (speakersGroupListMapper.selectByGroupId(groupId) == null) {
                speakersGroupListMapper.insert(groupId, false, true);
            } else {
                speakersGroupListMapper.updateIsBlacklistById(groupId, true);
            }
        } catch (PersistenceException e) {
            speakersGroupListMapper.createTable();
        }
    }


    @Override
    public void groupBlacklistDisable(long groupId) {
        try {
            if (speakersGroupListMapper.selectByGroupId(groupId) == null) {
                speakersGroupListMapper.insert(groupId, true, false);
            } else {
                speakersGroupListMapper.updateIsBlacklistById(groupId, false);
            }
        } catch (PersistenceException e) {
            speakersGroupListMapper.createTable();
        }
    }

    @Override
    public boolean groupBotOff(DiceMessageDTO data) {
        long id = data.getMessageEvent().getSelf_id();
        String regex = "^(.*\\[CQ:at,qq=" + id + "].*" + CacheReuseData.prefixMatchListRegex + "botoff|" + CacheReuseData.prefixMatchListRegex + "botoff)$";
        String messageSource = data.getBody().trim();
        if (messageSource.matches(regex)) {
            groupDisable(data.getMessageEvent().getGroup_id());
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    CustomText.getText("dice.bot.off"),
                    data.getWsServerEndpoint()
            );
            return true;
        }
        return false;
    }

    @Override
    public boolean groupBotOn(DiceMessageDTO data) {
        long id = data.getMessageEvent().getSelf_id();
        String regex = "^(.*\\[CQ:at,qq=" + id + "].*" + CacheReuseData.prefixMatchListRegex + "boton|" + CacheReuseData.prefixMatchListRegex + "boton)$";
        String messageSource = data.getBody().trim();
        if (messageSource.matches(regex)) {
            groupEnable(data.getMessageEvent().getGroup_id());
            shamrockService.sendGroupMessage(
                    data.getSanderId(),
                    data.getMessageEvent().getGroup_id(),
                    CustomText.getText("dice.bot.on"),
                    data.getWsServerEndpoint()
            );
            return true;
        }
        return false;
    }

    @Override
    public boolean isSpeakers(DiceMessageDTO diceMessageDTO) {
        long groupId = diceMessageDTO.getMessageEvent().getGroup_id();
        return groupIsEnable(groupId);
    }

    @Override
    public boolean isBlacklist(long id) {
        return groupIsBlacklist(id);
    }

    /*
    指令前缀检测，如果满足指令前缀，则返回该指令前缀文本，如果不满足，则返回null
     */
    @Override
    public String isPrefixMatch(String inputText) {
        if (inputText == null || inputText.equals("")) {
            return null;
        }
        for (String temp : CacheReuseData.prefixMatchList) {
            if (temp.length() > inputText.length()) {
                continue;
            }
            if (temp.equals(inputText.substring(0, temp.length()))) {
                return temp;
            }
        }
        return null;
    }

}
