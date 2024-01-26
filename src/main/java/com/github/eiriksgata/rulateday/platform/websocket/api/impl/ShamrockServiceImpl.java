package com.github.eiriksgata.rulateday.platform.websocket.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.github.eiriksgata.rulateday.platform.dice.config.GlobalData;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.WsServerEndpoint;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.WsRequestBean;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.WsResponseBean;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.FriedInfoVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.GroupInfoVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.GroupMessageVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.PrivateMessageVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShamrockServiceImpl implements ShamrockService {

    @Override
    public void sendPrivateMessage(Long userId, String message, WsServerEndpoint wsServerEndpoint) {
        List<MessageContent> messageContentList = new ArrayList<>();
        messageContentList.add(new MessageContent().setTypeByText(message));
        sendPrivateMessage(userId, messageContentList, wsServerEndpoint);
    }

    @Override
    public void sendGroupMessage(Long userId, Long groupId, String message, WsServerEndpoint wsServerEndpoint) {
        List<MessageContent> messageContentList = new ArrayList<>();
        if (GlobalData.configData.getBooleanValue("reply.at.user")) {
            messageContentList.add(new MessageContent().setTypeByAt(userId));
        }
        messageContentList.add(new MessageContent().setTypeByText(message));
        sendGroupMessage(userId, groupId, messageContentList, wsServerEndpoint);
    }

    @Override
    public void sendPrivateMessage(Long userId, List<MessageContent> messageContentList, WsServerEndpoint wsServerEndpoint) {
        WsRequestBean<PrivateMessageVo> requestBean = new WsRequestBean<>();
        requestBean.setAction("send_private_msg");
        PrivateMessageVo privateMessageVo = new PrivateMessageVo();
        privateMessageVo.setMessage(messageContentList);
        privateMessageVo.setUser_id(userId);
        requestBean.setEcho(UUID.randomUUID().toString());
        requestBean.setParams(privateMessageVo);
        wsServerEndpoint.sendMessage(JSONObject.toJSONString(requestBean));
    }

    @Override
    public void sendGroupMessage(Long userId, Long groupId, List<MessageContent> messageContentList, WsServerEndpoint wsServerEndpoint) {
        WsRequestBean<GroupMessageVo> requestBean = new WsRequestBean<>();
        requestBean.setAction("send_group_msg");
        GroupMessageVo groupMessageVo = new GroupMessageVo();
        groupMessageVo.setMessage(messageContentList);
        groupMessageVo.setGroup_id(groupId);
        requestBean.setEcho(UUID.randomUUID().toString());
        requestBean.setParams(groupMessageVo);
        wsServerEndpoint.sendMessage(JSONObject.toJSONString(requestBean));
    }

    @Override
    public List<GroupInfoVo> getGroupList(WsServerEndpoint wsServerEndpoint) {
        WsRequestBean<?> requestBean = new WsRequestBean<>();
        requestBean.setAction("get_group_list");
        requestBean.setEcho(UUID.randomUUID().toString());

        String resultText = wsServerEndpoint.sendSyncMessage(
                requestBean.getEcho(),
                JSONObject.toJSONString(requestBean));

        WsResponseBean<List<GroupInfoVo>> responseBean = JSONObject.parseObject(
                resultText,
                new TypeReference<ResponseBean<List<GroupInfoVo>>>() {
                }.getType());

        return responseBean.getData();
    }

    @Override
    public List<FriedInfoVo> getFriendList(WsServerEndpoint wsServerEndpoint) {
        WsRequestBean<?> requestBean = new WsRequestBean<>();
        requestBean.setAction("get_friend_list");
        requestBean.setEcho(UUID.randomUUID().toString());

        String resultText = wsServerEndpoint.sendSyncMessage(
                requestBean.getEcho(),
                JSONObject.toJSONString(requestBean));

        WsResponseBean<List<FriedInfoVo>> responseBean = JSONObject.parseObject(
                resultText,
                new TypeReference<ResponseBean<List<GroupInfoVo>>>() {
                }.getType());

        return responseBean.getData();

    }


}
