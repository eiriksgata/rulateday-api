package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivNpcRelRuleTageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FfxivNpcRelRuleTageMapper extends BaseMapper<FfxivNpcRelRuleTageDTO> {

    @Select("select * from ffxiv_npc_rel_rule_tage where character_id =#{characterId}")
    List<FfxivNpcRelRuleTageDTO> selectByCharacterId(@Param("characterId") Integer characterId);


}
