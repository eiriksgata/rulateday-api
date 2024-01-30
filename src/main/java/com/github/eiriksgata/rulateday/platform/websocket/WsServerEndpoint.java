package com.github.eiriksgata.rulateday.platform.websocket;

import cn.hutool.core.thread.ThreadUtil;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.utils.SpringContextUtil;
import com.github.eiriksgata.rulateday.platform.utils.ThreadPool;
import com.github.eiriksgata.rulateday.platform.websocket.api.ShamrockService;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api.AccountInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


@ServerEndpoint(value = "/ws/open-shamrock", configurator = GetHttpSessionConfigurator.class)
@Component
@Slf4j
public class WsServerEndpoint {

    public static final ConcurrentHashMap<String, WsServerEndpoint> channelList = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String, CompletableFuture<String>> responseFutures = new ConcurrentHashMap<>();

    private String authorization;

    public Session session;

    //TODO: 增加BOT QQ号
    private String userId;

    private String nickname;

    public String getAuthorization() {
        return authorization;
    }

    public Session getSession() {
        return session;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserId() {
        return userId;
    }


    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.userId = (String) config.getUserProperties().get("userId");
        log.info("config userId ,{}" , userId);

        this.authorization = (String) config.getUserProperties().get("authorization");
        this.session = session;

        ThreadPool.executorService.execute(() -> {
            //验证账号ID
            ShamrockService shamrockService = SpringContextUtil.getBean(ShamrockService.class);
            AccountInfoVo accountInfoVo = shamrockService.getLoginInfo(this);
            if (!Objects.equals(userId, accountInfoVo.getUser_id() + "")) {
                throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
            }
            this.nickname = accountInfoVo.getNickname();
            this.userId = accountInfoVo.getUser_id() + "";
        });

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
        ThreadPool.executorService.execute(() -> {
            EventHandler eventHandler = SpringContextUtil.getBean(EventHandler.class);
            eventHandler.implement(authorization, text);
        });
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        log.error("userId:{},link error:{}", authorization, throwable);
    }


    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
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
