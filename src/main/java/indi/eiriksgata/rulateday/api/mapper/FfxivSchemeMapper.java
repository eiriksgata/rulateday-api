package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivCardDTO;
import indi.eiriksgata.rulateday.api.pojo.ffxiv.FfxivSchemeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FfxivSchemeMapper extends BaseMapper<FfxivSchemeDTO> {


    @Select("select * from ffxiv_scheme")
    List<FfxivSchemeDTO> selectAll();

    @Select("select c.id,c.created_at,c.updated_at,c.rarity,\n" +
            "c.top,c.bottom,c.left,c.right,c.type,c.card_no,c.card_code,\n" +
            "c.name,c.describe,c.describe,c.title,c.patch\n" +
            "from ffxiv_card_data c \n" +
            "join ffxiv_scheme_rel_card fsrc \n" +
            "on fsrc.card_id = c.id \n" +
            "join ffxiv_scheme s \n" +
            "on s.id = fsrc.scheme_id \n" +
            "where s.id  = #{id}")
    List<FfxivCardDTO> selectCardsById(@Param("id") Integer id);

    @Select("select * from ffxiv_scheme where character_id =#{characterId}")
    List<FfxivSchemeDTO> selectByCharacterId(@Param("characterId") Integer characterId);


}

