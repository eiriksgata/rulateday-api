package com.github.eiriksgata.rulateday.platform.websocket;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;



@ServerEndpoint(value = "/ws/open-shamrock", configurator = GetHttpSessionConfigurator.class)
@Component
@Slf4j
public class WsServerEndpoint{

    public static ConcurrentHashMap<String, WsServerEndpoint> channelList = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String, CompletableFuture<String>> responseFutures = new ConcurrentHashMap<>();

    private String authorization;

    public Session session;

    public ShamrockService shamrockService;


    public String getAuthorization() {
        return authorization;
    }

    public Session getSession() {
        return session;
    }


    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.authorization = (String) config.getUserProperties().get("authorization");
        this.session = session;
        WsServerEndpoint.channelList.put(authorization, this);
        log.info("device link :" + authorization + ";device list size:" + channelList.size());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session) {
        channelList.remove(authorization);
        log.info("连接关闭:" + authorization + ";device list size:" + channelList.size());
    }

    /**
     * 接收到消息
     */
    @OnMessage
    public void onMessage(String text) {
        log.info(text);
        EventHandler eventHandler = SpringContextUtil.getBean(EventHandler.class);
        eventHandler.implement( text);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        log.error("userId:{},link error:{}", authorization, throwable);
    }


    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new CommonBaseException(CommonBaseExceptionEnum.ERROR);
        }
    }

    public String sendSyncMessage(String taskId, String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new CommonBaseException(CommonBaseExceptionEnum.ERROR);
        }
        CompletableFuture<String> responseFuture = new CompletableFuture<>();

        // 将 CompletableFuture 存储起来，以便在响应到达时完成异步操作
        responseFutures.put(taskId, responseFuture);

        // 等待响应，最多等待10秒（可以根据实际情况调整）
        try {
            AtomicReference<String> result = new AtomicReference<>();
            responseFuture.thenAccept(result::set).get(10, TimeUnit.SECONDS);
            return result.get();
        } catch (Exception e) {
            responseFutures.remove(taskId);
            e.printStackTrace();
            throw new CommonBaseException(CommonBaseExceptionEnum.WS_RESPONSE_TIMEOUT_ERROR);
        }
    }
}
