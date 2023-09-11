package com.github.eiriksgata.rulateday.platform.pojo.ffxiv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ffxiv_npc_rel_card")
public class FfxivNpcRelCardDTO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date createdAt;
    private Integer cardId;
    private Integer characterId;
    
    private Date updatedAt;

}
