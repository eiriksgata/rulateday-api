package com.github.eiriksgata.rulateday.platform.mapper;

import com.github.eiriksgata.rulateday.platform.pojo.CrazyDescribe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.mapper
 * date: 2020/11/4
 **/
@Mapper
public interface CrazyDescribeMapper {

    @Select("select * from coc7_crazy_describe")
    List<CrazyDescribe> selectAll();

}
