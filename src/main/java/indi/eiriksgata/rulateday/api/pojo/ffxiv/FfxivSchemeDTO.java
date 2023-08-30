package indi.eiriksgata.rulateday.api.pojo.ffxiv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ffxiv_scheme")
public class FfxivSchemeDTO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date createdAt;
    private String name;
    private String describe;
    private Integer characterId;
    private Date updatedAt;
    private String code;
    private String author;
    private Integer priority;


}
