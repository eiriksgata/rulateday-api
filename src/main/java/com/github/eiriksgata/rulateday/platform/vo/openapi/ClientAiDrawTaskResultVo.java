package com.github.eiriksgata.rulateday.platform.vo.openapi;

import lombok.Data;

@Data
public class ClientAiDrawTaskResultVo {

    private String clientId;
    private String code;
    private String pictureBase64;
    private String generationTime;
    private String description;


}
