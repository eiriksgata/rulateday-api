package indi.eiriksgata.rulateday.pojo.ffxiv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ffxiv_scheme_rel_card")
public class FfxivSchemeRelCardDTO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date createdAt;
    private Integer cardId;
    private Date updatedAt;
    private Integer schemeId;

}
