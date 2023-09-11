package com.github.eiriksgata.rulateday.platform.qo;

import lombok.Data;

/**
 * @author Snake
 * @date 2019/09/24
 */
@Data
public class UserQuery {
  /** 用户id */
  private Long uid;

  /** 账号免密令牌标识 */
  private String series;

  /** 用户账号 */
  private String name;
}
