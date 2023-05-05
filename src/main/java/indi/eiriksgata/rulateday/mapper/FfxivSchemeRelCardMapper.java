package indi.eiriksgata.rulateday.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivCardDTO;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivSchemeRelCardDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FfxivSchemeRelCardMapper extends BaseMapper<FfxivSchemeRelCardDTO> {

    @Delete("delete from ffxiv_scheme_rel_card where scheme_id =#{schemeId}")
    void deleteBySchemeId(@Param("schemeId") Integer schemeId);




}
