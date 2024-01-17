package com.github.eiriksgata.rulateday.platform.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cards_group_data")
public class CardsGroupData {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long groupId;
    private Long typeId;
    private String value;
    private Integer groupType;

}
