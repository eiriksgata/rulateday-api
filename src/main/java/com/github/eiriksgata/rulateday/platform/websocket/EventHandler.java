package com.github.eiriksgata.rulateday.platform.websocket;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class EventHandler {

    public void implement(String token, String payloadText) {
        JSONObject jsonObject = JSONObject.parseObject(payloadText);

        String taskId = jsonObject.getString("echo");
        if (taskId != null){
            CompletableFuture<String> responseFuture = WsServerEndpoint.responseFutures.get(taskId);
            if (responseFuture != null) {
                responseFuture.complete(payloadText);
                WsServerEndpoint.responseFutures.remove(taskId);
                return;
            }
        }


        String postType = jsonObject.getString("post_type");




    }



}
