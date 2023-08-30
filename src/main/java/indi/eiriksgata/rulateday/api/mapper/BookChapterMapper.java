package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.book.BookChapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BookChapterMapper extends BaseMapper<BookChapter> {

    @Select("select id , title from book_chapter where book_id = #{bookId}")
    List<BookChapter> selectChapterTitleByBookId(@Param("bookId") Integer bookId);


}
