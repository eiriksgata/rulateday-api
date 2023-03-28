package indi.eiriksgata.rulateday.api.websocket;

import com.alibaba.fastjson.JSONObject;
import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.api.exception.CommonBaseExceptionEnum;
import indi.eiriksgata.rulateday.api.utils.SpringContextUtil;
import indi.eiriksgata.rulateday.api.websocket.vo.WsDataBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint(value = "/push-channel", configurator = GetHttpSessionConfigurator.class)
@Slf4j
public class WsServerEndpoint {

    public static ConcurrentHashMap<String, WsServerEndpoint> channelList = new ConcurrentHashMap<>();

    private String userId;

    public Session session;

    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        session.setMaxIdleTimeout(1000 * 60);
        userId = (String) config.getUserProperties().get("userId");
        channelList.put(userId, this);
        this.session = session;
        log.info("device link :" + userId + ";device list size:" + channelList.size());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session) {
        channelList.remove(userId);
        log.info("连接关闭:" + userId + ";device list size:" + channelList.size());
    }

    /**
     * 接收到消息
     */
    @OnMessage
    public void onMessage(String text) {
        EventHandler eventHandler = SpringContextUtil.getBean(EventHandler.class);
        eventHandler.implement(userId, text);

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

    public void sendMessage(WsDataBean<?> message) {
        try {
            this.session.getBasicRemote().sendText(JSONObject.toJSONString(message));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new CommonBaseException(CommonBaseExceptionEnum.ERROR);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        log.error("userId:{},link error:{}", userId, throwable);
    }

}
