package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api;

import lombok.Data;

@Data
public class FriedInfoVo {
    private Long user_id;
    private String user_name;
    private String user_displayname;
    private String user_remark;
    private Integer age;
    private Integer gender;
    private Long group_id;
    private String platform;
    private String term_type;

}
