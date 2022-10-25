package indi.eiriksgata.rulatedayapi.websocket.vo;

import lombok.Data;

@Data
public class WsRequestBean<T> {
    private String eventId;
    private String currentTimestamp;
    private String eventType;

    private T data;


}
