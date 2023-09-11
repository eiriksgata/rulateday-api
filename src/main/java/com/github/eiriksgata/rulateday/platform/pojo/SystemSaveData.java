package com.github.eiriksgata.rulateday.platform.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("system_save_data")
public class SystemSaveData {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createdAt;

    private String name;
    private String description;
    private String path;
    private String value;

    private Date updatedAt;

}
