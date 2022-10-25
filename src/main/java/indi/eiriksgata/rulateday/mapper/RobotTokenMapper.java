package indi.eiriksgata.rulateday.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.pojo.RobotToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RobotTokenMapper extends BaseMapper<RobotToken> {

    @Select("select * from t_robot_token where name =#{name}")
    RobotToken selectByName(@Param("name") String name);

    @Select("select * from t_robot_token where machine_code =#{machineCode}")
    RobotToken selectByMachineCode(@Param("machineCode") String machineCode);

    @Select("select * from t_robot_token")
    List<RobotToken> selectAll();
}
