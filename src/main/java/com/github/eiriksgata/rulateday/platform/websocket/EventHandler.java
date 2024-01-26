package com.github.eiriksgata.rulateday.platform.websocket;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.dice.DiceMessageEventHandler;
import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.EventEnum;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class EventHandler {

    @Autowired
    DiceMessageEventHandler diceMessageEventHandler;

    public void implement(String channelId, String payloadText) {
        JSONObject jsonObject = JSONObject.parseObject(payloadText);
        String taskId = jsonObject.getString("echo");
        if (taskId != null) {
            CompletableFuture<String> responseFuture = WsServerEndpoint.responseFutures.get(taskId);
            if (responseFuture != null) {
                responseFuture.complete(payloadText);
                WsServerEndpoint.responseFutures.remove(taskId);
                return;
            }
        }

        String postType = jsonObject.getString("post_type");
        if (Objects.equals(postType, "meta_event")) {
            return;
        }

        log.info("{}", payloadText);
        DiceMessageDTO diceMessageDTO = new DiceMessageDTO();

        if (!Objects.equals(postType, EventEnum.PostType.MESSAGE.getName())) {
            return;
        }
        MessageEvent messageEvent = JSONObject.parseObject(
                payloadText, MessageEvent.class
        );
        diceMessageDTO.setSanderId(messageEvent.getUser_id());
        diceMessageDTO.setBody(messageEvent.getRaw_message());
        diceMessageDTO.setMessageEvent(messageEvent);
        diceMessageDTO.setWsServerEndpoint(
                WsServerEndpoint.channelList.get(channelId)
        );
        diceMessageEventHandler.OnDiceMessage(diceMessageDTO);

    }


}
