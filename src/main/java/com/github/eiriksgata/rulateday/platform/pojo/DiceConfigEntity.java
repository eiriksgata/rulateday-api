package com.github.eiriksgata.rulateday.platform.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

@Data
@TableName("dice_config")
public class DiceConfigEntity {

    @TableId("id")
    private Integer id;

    @TableField(value = "private_chat", jdbcType = JdbcType.INTEGER)
    private Integer private_chat;

    @TableField(value = "beta_version", jdbcType = JdbcType.INTEGER)
    private Integer beta_version;

}
