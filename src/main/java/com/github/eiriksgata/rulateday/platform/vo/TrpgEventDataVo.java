package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Data;

@Data
public class TrpgEventDataVo {

    private Integer eventId;
    private Integer previousEventId;
    private String title;
    private String content;
    private boolean firstNode;

}


