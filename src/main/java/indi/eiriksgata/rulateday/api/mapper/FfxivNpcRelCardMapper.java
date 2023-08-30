package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivNpcRelCardDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FfxivNpcRelCardMapper extends BaseMapper<FfxivNpcRelCardDTO> {

    @Select("select * from ffxiv_npc_rel_card where character_id =#{characterId}")
    List<FfxivNpcRelCardDTO> selectByCharacterId(@Param("characterId") Integer characterId);


    @Delete("delete from ffxiv_npc_rel_card where character_id = #{characterId}")
    void deleteByCharacterId(@Param("characterId") Integer characterId);


}
