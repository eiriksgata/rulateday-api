package indi.eiriksgata.rulateday.api.pojo.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName(value = "t_rbca_user_profile")
public class UserProfile {
  /** 主键 */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /** 姓名 */
  private String username;

  /** 昵称 */
  private String nickname;

  /** 头像图片 */
  private String icon;

  /** 简介 */
  private String intro;

  /** 性别 */
  private String sex;

  /** 生日 */
  //  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date birthday;

  /** 邮箱 */
  private String email;

  /** 手机 */
  private String mobile;

  /** 是否绑定手机 */
  @JsonIgnore private Boolean isMobileConfirmed;

  /** 是否绑定邮箱 */
  @JsonIgnore private Boolean isEmailConfirmed;

  /** 最后登录IP */
  @JsonIgnore private String lastLoginIp;

  /** 最后登录时间 */
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date lastLoginDate;

  /** 密码强度 */
  @JsonIgnore private Byte pwdIntensity;

  /** 手机登录标识 */
  @JsonIgnore private String mobileTgc;

  /** mac地址 */
  @JsonIgnore private String mac;

  /** 1=>WEB, 2=>IOS, 3=>ANDROID, 0=>Unknown */
  @JsonIgnore private Byte source;

  /** 激活类型，0：自动，1：手动 */
  @JsonIgnore private Boolean activeType;

  /** 注册时间 */
  @JsonIgnore
  //  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdAt;

  /** 修改日期 */
  @JsonIgnore
  //  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updatedAt;
}
