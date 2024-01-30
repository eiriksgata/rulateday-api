package com.github.eiriksgata.rulateday.platform.dice.dto;

import com.github.eiriksgata.rulateday.platform.websocket.WsServerEndpoint;
import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageEvent;
import com.github.eiriksgata.trpg.dice.vo.Message;
import lombok.Data;

@Data
public class DiceMessageDTO extends Message {
    private String body;
    private Long sanderId;

    private MessageEvent messageEvent;

    private WsServerEndpoint wsServerEndpoint;

}
