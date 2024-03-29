package com.github.eiriksgata.rulateday.platform.pojo.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "t_rbac_role")
public class Role {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
//    @JSONField(value = true, name = "id", serializeUsing = ToStringSerializer.class)
    private Long id;

    /**
     * 角色名
     */
    private String name;

    //角色编码
    private String code;

    /**
     * 角色值
     */
    private String intro;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createdAt;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updatedAt;

    @TableField(exist = false)
    List<Permission> permissions;

}
