package indi.eiriksgata.rulateday.api.pojo.ffxiv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ffxiv_card_data")
public class FfxivCardDTO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date createdAt;
    private Date updatedAt;
    private Integer rarity;
    private Integer top;
    private Integer bottom;
    private Integer left;
    private Integer right;
    private Integer type;
    private String cardNo;
    private String cardCode;
    private String name;
    private String describe;
    private String title;
    private String patch;


}
