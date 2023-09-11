package com.github.eiriksgata.rulateday.platform.pojo.book;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("book_category")
@Data
public class BookCategory {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createdAt;

    private String title;

    //上级分类ID
    private Integer pid;
    private Integer sort;
    private String metaTitle;
    private String metaKeywords;
    private String metaDescription ;

    private String icon = "";
    private Date updatedAt;

    private Integer status = 1;
    private Integer type = 0;


}
