package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.rbac.Role;
import indi.eiriksgata.rulateday.api.qo.RoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface RoleMapper extends BaseMapper<Role> {
  /**
   * select role
   *
   * @param roleQuery role query object
   * @return role
   */
  Role selectRole(@Param("roleQuery") RoleQuery roleQuery);

  /**
   * select all roles
   *
   * @param roleQuery role query object
   * @return roles list
   */
  List<Role> selectRoles(@Param("roleQuery") RoleQuery roleQuery);

  /**
   * select roles by user id
   *
   * @param userId user id
   * @return roles list
   */
  List<Role> selectByUserId(@Param("userId") Long userId);
}
