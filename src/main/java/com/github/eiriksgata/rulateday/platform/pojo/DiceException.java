package com.github.eiriksgata.rulateday.platform.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "dice_exception")
public class DiceException {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;
    private String content;
    private long createdTimestamp;
    private long qqId;

}
