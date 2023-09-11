package com.github.eiriksgata.rulateday.platform.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "trpg_event_schema")
public class TrpgEventSchemaEntity {

    private Integer id;
    private String createdAt;
    private String title;
    private String content;

    private Boolean firstNode;


}
