package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api;

import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import lombok.Data;

import java.util.List;

@Data
public class PrivateMessageVo {

    private Long user_id;
    private List<MessageContent> message;

    //是否解析CQ码
    private boolean auto_escape;

}
