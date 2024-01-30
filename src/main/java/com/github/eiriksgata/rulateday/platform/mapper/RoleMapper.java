package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from t_rbac_role name = #{name}")
    Role selectByName(@Param("name") String name);

    @Select("select * from t_rbac_role name like %#{name}%")
    List<Role> selectLikeName(@Param("name") String name);

    @Select("SELECT r.id,r.name,r.code,r.intro,r.created_at,r.updated_at\n" +
            "FROM t_rbac_rel_ur ur\n" +
            "INNER JOIN t_rbac_user u ON ur.user_id = u.id\n" +
            "INNER JOIN t_rbac_role r ON ur.role_id = r.id\n" +
            "WHERE u.id = #{userId}")
    List<Role> selectByUserId(@Param("userId") Long userId);

    @Select("select role_id from t_rbac_rel_ur where user_id =#{userId}")
    List<Long> selectRoleIdByUserId(@Param("userId") Long userId);

    @Results(id = "selectAllRolePermission", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "code", property = "code"),
            @Result(column = "intro", property = "intro"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(property = "permissions", column = "id", javaType = List.class,
                    many = @Many(select = "com.github.eiriksgata.rulateday.platform.mapper.PermissionMapper.selectByRoleId")
            )
    })
    @Select({"select * from t_rbac_role"})
    List<Role> selectAllRolePermission();

    @Select("select * from t_rbac_role")
    List<Role> selectAllRole();

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "code", property = "code"),
            @Result(column = "intro", property = "intro"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(property = "permissions", column = "id", javaType = List.class,
                    many = @Many(select = "com.github.eiriksgata.rulateday.platform.mapper.PermissionMapper.selectByRoleId")
            )
    })
    @Select("select * from t_rbac_role where id =#{roleId}")
    Role selectRolePermissionByRoleId(@Param("roleId") Long roleId);

}
