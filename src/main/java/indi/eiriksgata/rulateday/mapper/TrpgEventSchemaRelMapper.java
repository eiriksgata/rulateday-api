package indi.eiriksgata.rulateday.mapper;

import indi.eiriksgata.rulateday.pojo.TrpgEventSchemaRelEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TrpgEventSchemaRelMapper {

    @Select("select * from trpg_event_schema_rel where event_id =#{eventId}")
    List<TrpgEventSchemaRelEntity> selectByEventId(@Param("eventId") Integer eventId);

    @Insert("insert into trpg_event_schema_rel (created_at,event_id,next_event_id) values (#{createdAt},#{eventId},#{nextEventId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertResultId(TrpgEventSchemaRelEntity entity);

    @Delete("delete from trpg_event_schema_rel where next_event_id = #{nextEventId}")
    void deleteByNextEventId(@Param("nextEventId") Integer nextEventId);


    @Delete("delete from trpg_event_schema_rel where event_id = #{eventId} and next_event_id = #{nextEventId}")
    void deleteByLink(@Param("eventId") Integer eventId, @Param("nextEventId") Integer nextEventId);

}
