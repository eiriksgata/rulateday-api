package indi.eiriksgata.rulateday.api.mapper;

import indi.eiriksgata.rulateday.api.pojo.CrazyDescribe;
import org.apache.ibatis.annotations.Mapper;
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
public interface CrazyDescribeMapper {
    @Select("select * from coc7_crazy_describe")
    List<CrazyDescribe> selectAll();

}
