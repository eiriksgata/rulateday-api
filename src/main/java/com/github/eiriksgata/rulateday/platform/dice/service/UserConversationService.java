package com.github.eiriksgata.rulateday.platform.dice.service;



import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.pojo.QueryDataBase;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.service
 * date: 2021/2/20
 **/
public interface UserConversationService {
    void saveConversation(Long qq, List<QueryDataBase> queryData);

    String checkInputQuery(DiceMessageDTO data);
}
