package indi.eiriksgata.rulateday.api.pojo.ffxiv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ffxiv_npc")
public class FfxivNpcDTO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date createdAt = new Date();
    private String name;
    private String describe;
    private String code;
    private Date updatedAt = new Date();
    private String position;

    @TableField(exist = false)
    private String pictureBase64;

}
