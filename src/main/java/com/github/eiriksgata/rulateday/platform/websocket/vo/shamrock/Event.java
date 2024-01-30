package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock;

import lombok.Data;

@Data
public class Event {

    private Long time;
    private Long self_id;
    private String post_type;

}
