package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageEvent extends Event {

    private String message_type;
    private String sub_type;
    private Long message_id;
    private Long user_id;
    private List<MessageContent> message = new ArrayList<>();
    private String raw_message;
    private Integer font;
    private MessageSender sender;
    private Long group_id;
    private Long target_id;
    private Integer temp_source;
    private Long peer_id;


    public void addMessage(String text) {
        message.add(new MessageContent().setTypeByText(text));
    }

    public void addMessage(Long qq) {
        message.add(new MessageContent().setTypeByAt(qq));
    }


}
