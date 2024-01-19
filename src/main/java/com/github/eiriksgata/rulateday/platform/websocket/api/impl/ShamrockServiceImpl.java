package com.github.eiriksgata.rulateday.platform.websocket.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.WsServerEndpoint;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.WsRequestBean;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.WsResponseBean;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.GroupInfoVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.GroupMessageVo;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.PrivateMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShamrockServiceImpl implements ShamrockService {

    private final WsServerEndpoint wsServerEndpoint;

    @Autowired
    public ShamrockServiceImpl(WsServerEndpoint wsServerEndpoint) {
        this.wsServerEndpoint = wsServerEndpoint;
    }

    @Override
    public void sendPrivateMessage(Long userId, String message) {
        WsRequestBean<PrivateMessageVo> requestBean = new WsRequestBean<>();
        requestBean.setAction("send_private_msg");

        PrivateMessageVo privateMessageVo = new PrivateMessageVo();

        List<MessageContent> messageContentList = new ArrayList<>();
        messageContentList.add(new MessageContent().setTypeByText(message));

        privateMessageVo.setMessage(messageContentList);
        privateMessageVo.setUser_id(userId);

        requestBean.setEcho(UUID.randomUUID().toString());
        requestBean.setParams(privateMessageVo);


        wsServerEndpoint.sendMessage(JSONObject.toJSONString(requestBean));
    }

    @Override
    public void sendGroupMessage(Long userId, Long groupId, String message) {
        WsRequestBean<GroupMessageVo> requestBean = new WsRequestBean<>();
        requestBean.setAction("send_group_msg");

        GroupMessageVo groupMessageVo = new GroupMessageVo();

        List<MessageContent> messageContentList = new ArrayList<>();
        messageContentList.add(new MessageContent().setTypeByAt(userId));
        messageContentList.add(new MessageContent().setTypeByText(message));

        groupMessageVo.setMessage(messageContentList);
        groupMessageVo.setGroup_id(groupId);

        requestBean.setEcho(UUID.randomUUID().toString());
        requestBean.setParams(groupMessageVo);

        wsServerEndpoint.sendMessage(JSONObject.toJSONString(requestBean));
    }

    @Override
    public void sendPrivateMessage(Long userId, MessageContent messageContent) {
        WsRequestBean<PrivateMessageVo> requestBean = new WsRequestBean<>();
        requestBean.setAction("send_private_msg");

        PrivateMessageVo privateMessageVo = new PrivateMessageVo();

        List<MessageContent> messageContentList = new ArrayList<>();
        messageContentList.add(messageContent);

        privateMessageVo.setMessage(messageContentList);
        privateMessageVo.setUser_id(userId);

        requestBean.setEcho(UUID.randomUUID().toString());
        requestBean.setParams(privateMessageVo);


        wsServerEndpoint.sendMessage(JSONObject.toJSONString(requestBean));
    }

    @Override
    public void sendGroupMessage(Long userId, Long groupId, MessageContent messageContent) {
        WsRequestBean<GroupMessageVo> requestBean = new WsRequestBean<>();
        requestBean.setAction("send_group_msg");

        GroupMessageVo groupMessageVo = new GroupMessageVo();

        List<MessageContent> messageContentList = new ArrayList<>();
        messageContentList.add(messageContent);

        groupMessageVo.setMessage(messageContentList);
        groupMessageVo.setGroup_id(groupId);

        requestBean.setEcho(UUID.randomUUID().toString());
        requestBean.setParams(groupMessageVo);

        wsServerEndpoint.sendMessage(JSONObject.toJSONString(requestBean));
    }

    @Override
    public List<GroupInfoVo> getGroupList() {
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


}
