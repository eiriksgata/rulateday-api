package indi.eiriksgata.rulateday.api.pojo.ffxiv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ffxiv_npc_rel_rule_tag")
public class FfxivNpcRelRuleTageDTO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date createdAt;
    private Integer characterId;
    private Integer tagId;
    private Date updatedAt;

}
