package com.github.eiriksgata.rulateday.platform.constant;

import java.util.ArrayList;
import java.util.List;

public enum CacheNameEnum {

    USER("user", "用户信息"),

    ROLES_PERMISSIONS("role", "角色信息(包含对应权限)"),
    PERMISSIONS("permissions", "所有权限信息");


    private final String name;

    private final String describe;

    CacheNameEnum(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public static List<String> getCacheNames() {
        List<String> cacheNameList = new ArrayList<>(CacheNameEnum.values().length);
        CacheNameEnum[] values = CacheNameEnum.values();
        for (int i = 0; i < CacheNameEnum.values().length; i++) {
            cacheNameList.add(values[i].name);
        }
        return cacheNameList;
    }

    public String getCacheName() {
        return name;
    }
}
