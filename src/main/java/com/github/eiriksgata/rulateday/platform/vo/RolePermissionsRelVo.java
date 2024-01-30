package com.github.eiriksgata.rulateday.platform.vo;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionsRelVo {
    private Long roleId;
    private List<Long> permissions;
}
