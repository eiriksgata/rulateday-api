package com.github.eiriksgata.rulateday.platform.websocket.api;

import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.GroupInfoVo;

import java.util.List;

public interface ShamrockService {


    void sendPrivateMessage(Long id, String message);

    void sendGroupMessage(Long userId, Long groupId, String message);

    void sendPrivateMessage(Long userId, MessageContent messageContent);

    void sendGroupMessage(Long userId, Long groupId, MessageContent messageContent);

    List<GroupInfoVo> getGroupList();
}
