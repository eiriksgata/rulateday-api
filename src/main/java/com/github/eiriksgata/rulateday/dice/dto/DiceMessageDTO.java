package com.github.eiriksgata.rulateday.dice.dto;

import com.github.eiriksgata.rulateday.platform.websocket.WsServerEndpoint;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.WsRequestBean;
import com.github.eiriksgata.trpg.dice.vo.Message;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiceMessageDTO extends Message {
    private String body;
    private Long sanderId;

    private WsRequestBean<com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.Message> wsRequestBean;

    private WsServerEndpoint wsServerEndpoint;

}
