package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivSchemeRelCardDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FfxivSchemeRelCardMapper extends BaseMapper<FfxivSchemeRelCardDTO> {

    @Delete("delete from ffxiv_scheme_rel_card where scheme_id =#{schemeId}")
    void deleteBySchemeId(@Param("schemeId") Integer schemeId);




}
