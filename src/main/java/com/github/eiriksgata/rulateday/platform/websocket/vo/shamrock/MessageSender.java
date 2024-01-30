package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock;

import lombok.Data;

@Data
public class MessageSender {
    private Long user_id;
    private String nickname;
    private String card;
    private String level;
    private String role;
    private String title;


}
