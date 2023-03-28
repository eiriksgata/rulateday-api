package indi.eiriksgata.rulateday.api.websocket.vo;

import lombok.Data;

@Data
public class SendGroupMessageVo {

    private Long groupId;
    private Long senderId;
    private String text;

    private String pictureBase64;

}
