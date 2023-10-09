package com.github.eiriksgata.rulateday.platform.mapper;

import com.github.eiriksgata.rulateday.platform.pojo.CrazyOverDescribe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.mapper
 * date: 2020/11/4
 **/
@Mapper
@Repository
public interface CrazyOverDescribeMapper {

    @Select("select * from coc7_crazy_over_describe")
    List<CrazyOverDescribe> selectAll();

}
