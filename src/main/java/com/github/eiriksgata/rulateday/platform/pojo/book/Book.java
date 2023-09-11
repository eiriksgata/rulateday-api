package com.github.eiriksgata.rulateday.platform.pojo.book;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("book_list")
@Data
public class Book {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createdAt;

    private Integer categoryId;

    private String title;
    private String author;
    private String picture;
    private String content;

    private Date updatedAt;

}
