package indi.eiriksgata.rulateday.pojo.ffxiv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ffxiv_npc_rule_tag")
public class FfxivNpcRuleTagDTO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date createdAt;
    private String name;
    private Date updatedAt;

}
