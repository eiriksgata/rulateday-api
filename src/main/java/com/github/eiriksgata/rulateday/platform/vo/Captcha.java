package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Captcha {
    private String code;
}
