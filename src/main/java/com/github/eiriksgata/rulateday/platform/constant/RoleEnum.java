package com.github.eiriksgata.rulateday.platform.constant;

public enum RoleEnum {

    ADMIN("管理员", "ADMIN"),
    OPS("运维", "OPS"),
    GUEST("游客", "GUEST");

    private final String name;
    private final String code;

    RoleEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }


}
