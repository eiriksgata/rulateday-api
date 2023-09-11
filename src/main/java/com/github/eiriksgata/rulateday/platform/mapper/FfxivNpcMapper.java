package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivCardDTO;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivNpcDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FfxivNpcMapper extends BaseMapper<FfxivNpcDTO> {


    @Select("select * from ffxiv_npc")
    List<FfxivNpcDTO> selectAll();

    @Select("select c.id,c.created_at,c.updated_at,c.rarity,\n" +
            "c.top,c.bottom,c.left,c.right,c.type,c.card_no,c.card_code,\n" +
            "c.name,c.describe,c.describe,c.title,c.patch\n" +
            "from ffxiv_card_data c \n" +
            "join ffxiv_npc_rel_card nrc \n" +
            "on nrc.card_id = c.id \n" +
            "join ffxiv_npc n \n" +
            "on n.id = nrc.character_id \n" +
            "where n.id  = #{id}")
    List<FfxivCardDTO> selectCardsById(@Param("id") Integer id);

}
