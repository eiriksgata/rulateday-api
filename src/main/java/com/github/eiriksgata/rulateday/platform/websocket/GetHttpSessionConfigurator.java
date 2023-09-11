package com.github.eiriksgata.rulateday.platform.websocket;

import com.github.eiriksgata.rulateday.platform.service.RobotTokenService;
import com.github.eiriksgata.rulateday.platform.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;


@Configuration
@Slf4j
public class GetHttpSessionConfigurator extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // TODO Auto-generated method stub
        String authorization = request.getHeaders().get("authorization").get(0);
        String userId = request.getHeaders().get("userId").get(0);
        if (authorization != null && userId != null) {
            if (WsServerEndpoint.channelList.get(userId) == null) {
                RobotTokenService robotTokenService = SpringContextUtil.getBean(RobotTokenService.class);
                robotTokenService.cryptoHeadersVerification(userId, authorization);
                sec.getUserProperties().put("authorization", authorization);
                sec.getUserProperties().put("userId", userId);
            } else {
                log.info("device link fail : device existence , userId:{}", userId);
                return;
            }
        }
        log.info("modify handshake:" + userId);
    }


}