package indi.eiriksgata.rulateday.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.pojo.AiTextDrawTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AiTextDrawMapper extends BaseMapper<AiTextDrawTask> {


    @Select("select * from t_ai_text_draw_task where user_id =#{userId}")
    AiTextDrawTask selectByUserId(@Param("userId") String id);


}
