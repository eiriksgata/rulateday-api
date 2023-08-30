package indi.eiriksgata.rulateday.api.pojo.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "t_rbca_user")
public class User {
  /** 主键 */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /** 账户名 */
  private String name;

  /** 密码 */
  private String password;

  private Integer type;

  /** 免密令牌标识 */
  @JsonIgnore private String series;

  /** 免密令牌 */
  @JsonIgnore private String token;

  /** 账户是否有效 */
  @JsonIgnore private Boolean isEnabled;

  /** 账户是否删除 */
  @JsonIgnore private Boolean isDeleted;

  /** 账户是否冻结 */
  @JsonIgnore private Boolean isExpired;

  /** 账户是否过期 */
  @JsonIgnore private Boolean isLocked;

  /** 密码是否过期 */
  @JsonIgnore private Boolean isPasswdExpired;

  /** 创建时间 */
  @JsonIgnore private Date createdAt;

  /** 修改时间 */
  @JsonIgnore private Date updatedAt;

  /** roles */
  @TableField(exist = false)
  private List<Role> roles;

  /** permissions */
  @TableField(exist = false)
  private List<Permission> permissions;

  /** profile */
  @TableField(exist = false)
  private UserProfile profile;
}
