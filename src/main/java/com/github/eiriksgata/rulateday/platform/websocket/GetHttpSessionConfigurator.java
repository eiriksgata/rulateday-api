package com.github.eiriksgata.rulateday.platform.websocket;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.service.RobotService;
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
        if (authorization != null) {
            authorization = authorization.substring("bearer ".length());
            sec.getUserProperties().put("authorization", authorization);
            if (WsServerEndpoint.channelList.get(authorization) == null) {
                RobotService robotService = SpringContextUtil.getBean(RobotService.class);
                robotService.cryptoHeadersVerification(authorization);
            } else {
                try {
                    WsServerEndpoint.channelList.get(authorization).session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("尝试关闭重复链接失败 token:{}", authorization);
                }
                log.info("device link fail : device existence , authorization:{}", authorization);
            }
        } else {
            throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
        }
        log.info("modify handshake:" + authorization);
    }


}