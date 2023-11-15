package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SliderCaptchaVerifyVo {

    private String username;
    private int offset = 0;

}
