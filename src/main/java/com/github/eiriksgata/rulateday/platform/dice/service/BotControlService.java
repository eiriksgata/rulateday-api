package com.github.eiriksgata.rulateday.platform.dice.service;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service
 * date: 2020/11/13
 **/
public interface BotControlService {
    boolean groupIsEnable(long groupId);

    boolean groupIsBlacklist(long groupId);

    void groupEnable(long groupId);

    void groupDisable(long groupId);

    void groupBlacklistEnable(long groupId);

    void groupBlacklistDisable(long groupId);

    boolean groupBotOff(DiceMessageDTO data);

    boolean groupBotOn(DiceMessageDTO data);

    boolean isSpeakers(DiceMessageDTO diceMessageDTO);

    boolean isBlacklist(long id);

    /*
        指令前缀检测，如果满足指令前缀，则返回该指令前缀文本，如果不满足，则返回null
         */
    String isPrefixMatch(String inputText);
}
