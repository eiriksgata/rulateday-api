package indi.eiriksgata.rulateday.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.pojo.InfiniteLibOperationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InfiniteLibOperationRecordMapper extends BaseMapper<InfiniteLibOperationRecord> {

    @Select("select * from infinite_lib_operation_record where name = #{name}")
    List<InfiniteLibOperationRecord> selectAllByName(@Param("name") String name);



}
