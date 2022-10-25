package indi.eiriksgata.rulatedayapi.websocket.vo;

import lombok.Data;

@Data
public class HeartbeatVo {

    private String userId;
    private String currentTimestamp;

}
