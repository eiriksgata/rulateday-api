package indi.eiriksgata.rulateday.api.mapper;

import indi.eiriksgata.rulateday.api.pojo.RuleBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulateday.mapper
 * date: 2020/11/4
 **/

@Mapper
@Repository
public interface RuleBookMapper {

    @Select("select * from coc7_rule_book where title like #{title} ")
    RuleBook selectByTitle(@Param("title") String title);

    @Select("select * from coc7_rule_book")
    List<RuleBook> selectAll();


}
