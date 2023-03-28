package indi.eiriksgata.rulateday.api.websocket.vo;

import lombok.Data;

@Data
public class WsDataBean<T> {
    private String eventId;
    private long currentTimestamp;
    private String eventType;

    private T data;


}
