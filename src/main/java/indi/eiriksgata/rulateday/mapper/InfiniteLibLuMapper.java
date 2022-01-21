package indi.eiriksgata.rulateday.mapper;

import indi.eiriksgata.rulateday.pojo.QueryDataBase;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InfiniteLibLuMapper {

    @Insert("insert into infinite_lib_lu (name,describe) values (#{name},#{describe})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(QueryDataBase queryDataBase);

    @Select("select * from infinite_lib_lu")
    List<QueryDataBase> selectAll();

    @Select("select name from infinite_lib_lu")
    List<QueryDataBase> selectAllName();

    @Select("select * from infinite_lib_lu where id =#{id}")
    QueryDataBase selectById(@Param("id") Long id);

    @Select("select * from infinite_lib_lu where name =#{name}")
    QueryDataBase selectByName(@Param("name") String name);

    @Select("select * from infinite_lib_lu where name like #{name} LIMIT 20")
    List<QueryDataBase> selectLikeName(@Param("name") String name);

    @Update("update infinite_lib_lu set name=#{name},describe=#{describe} where id=#{id}")
    void updateById(@Param("id") long id, @Param("name") String name, @Param("describe") String describe);

    @Delete("delete from infinite_lib_lu where id = #{id}")
    void deleteById(@Param("id") Long id);

    @Delete("delete from infinite_lib_lu where name = #{name}")
    void deleteByName(@Param("name") String name);
}
