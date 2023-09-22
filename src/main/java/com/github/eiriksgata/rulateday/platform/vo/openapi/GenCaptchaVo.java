package com.github.eiriksgata.rulateday.platform.vo.openapi;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenCaptchaVo {

    private String codeId;
    private String pictureBase64;

}
