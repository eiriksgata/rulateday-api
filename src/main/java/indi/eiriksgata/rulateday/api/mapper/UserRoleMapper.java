package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.rbac.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
  /**
   * batch insert user-role
   *
   * @param urList user roles
   * @return primary key
   */
  Long batchInsert(@Param("urList") List<UserRole> urList);

  /**
   * delete all roles by user id
   *
   * @param userId user id
   * @return true or false
   */
  Long deleteByUserId(@Param("userId") Long userId);
}
