package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.DiceException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.github.eiriksgata.rulateday.mapper
 * date: 2021/5/20
 **/
@Mapper
@Repository
public interface DiceExceptionMapper extends BaseMapper<DiceException> {

    @Select("select * from dice_exception order by created_timestamp desc")
    List<DiceException> selectAll();

}
