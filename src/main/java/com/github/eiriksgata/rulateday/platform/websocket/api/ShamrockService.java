package com.github.eiriksgata.rulateday.platform.websocket.api;

import com.github.eiriksgata.rulateday.platform.websocket.WsServerEndpoint;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.AccountInfoVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.FriedInfoVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.GroupInfoVo;

import java.util.List;

public interface ShamrockService {


    void sendPrivateMessage(Long id, String message, WsServerEndpoint wsServerEndpoint);

    void sendGroupMessage(Long userId, Long groupId, String message, WsServerEndpoint wsServerEndpoint);

    void sendPrivateMessage(Long userId, List<MessageContent> messageContentList, WsServerEndpoint wsServerEndpoint);

    void sendGroupMessage(Long userId, Long groupId, List<MessageContent> messageContentList, WsServerEndpoint wsServerEndpoint);

    List<GroupInfoVo> getGroupList(WsServerEndpoint wsServerEndpoint);

    List<FriedInfoVo> getFriendList(WsServerEndpoint wsServerEndpoint);

    void quitGroup(Long groupId, WsServerEndpoint wsServerEndpoint);

    AccountInfoVo getLoginInfo(WsServerEndpoint wsServerEndpoint);
}
