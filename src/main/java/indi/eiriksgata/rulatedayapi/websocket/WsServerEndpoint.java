package indi.eiriksgata.rulatedayapi.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint(value = "/push-channel", configurator = GetHttpSessionConfigurator.class)
@Slf4j
public class WsServerEndpoint {

    public static ConcurrentHashMap<String, WsServerEndpoint> channelList = new ConcurrentHashMap<>();

    private String userId;

    @Autowired
    EventHandler eventHandler;

    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        session.setMaxIdleTimeout(1000 * 60);
        userId = (String) config.getUserProperties().get("userId");
        channelList.put(userId, this);
        log.info("device link :" + userId + ";device list size:" + channelList.size());
    }

    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        channelList.remove(userId);
        log.info("连接关闭:" + userId + ";device list size:" + channelList.size());
    }

    /**
     * 接收到消息
     *
     * @param text
     */
    @OnMessage
    public void onMessage(String text) {
        JSONObject jsonObject = JSONObject.parseObject(text);
        String eventType = jsonObject.getString("eventType");
        switch (eventType) {

        }
        //WsRequestBean<?> requestBean = JSONObject.parseObject(text, )


    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        log.error("userId:{},link error:{}", userId, throwable);
    }

}
