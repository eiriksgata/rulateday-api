package com.github.eiriksgata.rulateday.platform.pojo.rbac;

import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "t_rbac_permission")
public class Permission {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级权限id(0=>顶级权限)
     */
    private Long parentId;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限别名
     */
    private String alias;


    private String action;

    /**
     * 权限说明
     */
    @JsonIgnore
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

    /**
     * 子权限
     */
    @TableField(exist = false)
    private List<Permission> children;
}
