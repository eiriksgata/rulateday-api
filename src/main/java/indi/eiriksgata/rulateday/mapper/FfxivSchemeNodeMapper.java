package indi.eiriksgata.rulateday.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.pojo.ffxiv.FfxivSchemeNodeDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FfxivSchemeNodeMapper extends BaseMapper<FfxivSchemeNodeDTO> {

    @Delete("delete from ffxiv_scheme_node where scheme_id =#{schemeId}")
    void deleteBySchemeId(@Param("schemeId") Integer schemeId);

    @Select("select * from ffxiv_scheme_node where scheme_id =#{schemeId}")
    List<FfxivSchemeNodeDTO> selectBySchemeId(@Param("schemeId") Integer schemeId);

    @Select("with recursive" +
            "    tempTable as (" +
            "        select * from ffxiv_scheme_node where id = #{nodeId}" +
            "        union all" +
            "        select ffxiv_scheme_node.* " +
            "        from tempTable join ffxiv_scheme_node " +
            "        on tempTable.id = ffxiv_scheme_node.parent_id" +
            "    )" +
            "select * from tempTable ")
    List<FfxivSchemeNodeDTO> selectNodeThreeByNodeId(@Param("nodeId") Integer nodeId);


}
