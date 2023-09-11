package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.ffxiv.FfxivCardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FfxivCardEditMapper extends BaseMapper<FfxivCardDTO> {

    @Select("select * from ffxiv_card_data")
    List<FfxivCardDTO> selectAll();



}
