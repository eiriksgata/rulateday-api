package indi.eiriksgata.rulateday.api.pojo.book;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("book_chapter")
public class BookChapter {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createdAt;

    private Date updatedAt;
    private Integer bookId;
    private String source;
    private String status;

    private String chapter;
    private String title;

}
