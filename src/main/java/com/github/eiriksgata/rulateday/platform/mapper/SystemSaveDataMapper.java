package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.SystemSaveData;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SystemSaveDataMapper extends BaseMapper<SystemSaveData> {

    @Select("select * from system_save_data where name = #{name}")
    SystemSaveData selectByName(@Param("name") String name);


}
