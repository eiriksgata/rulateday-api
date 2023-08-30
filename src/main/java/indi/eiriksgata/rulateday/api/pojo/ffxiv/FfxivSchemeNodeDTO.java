package indi.eiriksgata.rulateday.api.pojo.ffxiv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("ffxiv_scheme_node")
@Data
public class FfxivSchemeNodeDTO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    private String name;
    private Integer schemeId;

    private Integer parentId;

    private Integer cardId = -1;
    private Integer cardPosition = 0;
    private String describe;
    private Integer first = -1;


}
