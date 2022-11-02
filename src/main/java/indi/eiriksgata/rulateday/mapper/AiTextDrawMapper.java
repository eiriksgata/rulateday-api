package indi.eiriksgata.rulateday.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.pojo.AiTextDrawTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface AiTextDrawMapper extends BaseMapper<AiTextDrawTask> {

    @Select("select * from t_ai_text_draw_task where created_by_id =#{createdById} and is_complete = 0")
    AiTextDrawTask selectByCreatedById(@Param("createdById") long createdById);

    @Select("select count(*) from t_ai_text_draw_task where is_complete = 0")
    int selectCurrentTaskSize();

    @Update("update t_ai_text_draw_task set is_enable = 0 where is_enable = 1 and is_complete = 0 and ( #{currentTimestamp} - updated_at )  > 120000")
    void updateTimeoutTask(@Param("currentTimestamp") long currentTimestamp);

    @Select("select * from t_ai_text_draw_task where is_enable = 0 and is_complete = 0 order by created_at limit 1")
    AiTextDrawTask getTask();

    @Update("update t_ai_text_draw_task set is_enable = 1 , client_id = #{clientId} , updated_at = #{updatedAt} where id = #{id}")
    void updateTaskReceiveState(@Param("clientId") String clientId, @Param("updatedAt") Date updatedAt, @Param("id") Integer id);

    @Select("select * from t_ai_text_draw_task where code = #{code} and client_id =#{clientId} and is_enable = 1 and is_complete = 0")
    AiTextDrawTask selectByCodeAndClientId(@Param("code") String code, @Param("clientId") String clientId);


}
