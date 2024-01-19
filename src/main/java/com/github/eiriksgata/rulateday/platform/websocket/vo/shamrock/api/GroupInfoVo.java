package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock.api;

import lombok.Data;

import java.util.List;

@Data
public class GroupInfoVo {

    private Long group_id;
    private String groupName;
    private String group_remark;
    private Long group_uin;
    private List<Long> admins;
    private String class_text;
    private boolean is_frozen;
    private Integer max_member;
    private Integer max_member_count;
    private Integer member_num;
    private Integer member_count;


}
