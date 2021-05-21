package indi.eiriksgata.rulateday.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.pojo.DiceException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulateday.mapper
 * date: 2021/5/20
 **/
@Mapper
public interface DiceExceptionMapper extends BaseMapper<DiceException> {

    @Select("select * from dice_exception")
    List<DiceException> selectAll();

}
