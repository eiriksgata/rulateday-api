package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_rbac_user where name =#{name}")
    User selectByName(@Param("name") String username);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "password", property = "password"),
            @Result(column = "series", property = "series"),
            @Result(column = "token", property = "token"),
            @Result(column = "is_enabled", property = "isEnabled"),
            @Result(column = "is_deleted", property = "isDeleted"),
            @Result(column = "is_expired", property = "isExpired"),
            @Result(column = "is_locked", property = "isLocked"),
            @Result(column = "is_password_expired", property = "isPasswordExpired"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(property = "roles", column = "id", javaType = List.class,
                    many = @Many(select = "com.github.eiriksgata.rulateday.platform.mapper.RoleMapper.selectByUserId")
            )
    })
    @Select("select * from t_rbac_user where id = #{id}")
    User selectUrp(@Param("id") Long userId);

    @Update("update t_rbac_user set password = #{password} where id = #{id}")
    void updatePasswordByUserId(@Param("password") String password, @Param("id") long userId);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "password", property = "password"),
            @Result(column = "series", property = "series"),
            @Result(column = "token", property = "token"),
            @Result(column = "is_enabled", property = "isEnabled"),
            @Result(column = "is_deleted", property = "isDeleted"),
            @Result(column = "is_expired", property = "isExpired"),
            @Result(column = "is_locked", property = "isLocked"),
            @Result(column = "is_password_expired", property = "isPasswordExpired"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(property = "roles", column = "id", javaType = List.class,
                    many = @Many(select = "com.github.eiriksgata.rulateday.platform.mapper.RoleMapper.selectRoleIdByUserId")
            )
    })
    @Select("select * from t_rbac_user")
    List<User> selectUrpList();

}
