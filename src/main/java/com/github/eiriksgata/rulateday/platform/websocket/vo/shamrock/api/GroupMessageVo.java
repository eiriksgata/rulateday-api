package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api;

import com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.MessageContent;
import lombok.Data;

import java.util.List;

@Data
public class GroupMessageVo {

    private Long group_id;
    private List<MessageContent> message;
    private boolean auto_escape;

}
