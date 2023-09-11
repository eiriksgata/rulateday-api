package com.github.eiriksgata.rulateday.platform.mapper;

import com.github.eiriksgata.rulateday.platform.pojo.TrpgEventSchemaEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TrpgEventSchemaMapper {

    @Insert("insert into trpg_event_schema (created_at,title,content,first_node) values(#{createdAt},#{title},#{content},#{firstNode})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertResultId(TrpgEventSchemaEntity trpgEventSchemaEntity);

    @Select("select * from trpg_event_schema where first_node = 1")
    List<TrpgEventSchemaEntity> queryAllFirstNode();

    @Select("select * from trpg_event_schema where id = #{id}")
    TrpgEventSchemaEntity selectById(@Param("id") Integer id);

    @Delete("delete from trpg_event_schema where id = #{id}")
    void deleteById(@Param("id") Integer id);

}
