package com.github.eiriksgata.rulateday.platform.exception;

import lombok.Getter;

@Getter
public enum CommonBaseExceptionEnum {

    ACCOUNTS_AUTHENTICATION_ERROR(10801, "账号密码错误"),

    ACCOUNTS_USERNAME_LENGTH_ERROR(11310, "账号名过短"),

    ACCOUNTS_USERNAME_EXIST_ERROR(11311, "账号已存在"),
    ACCOUNTS_USERNAME_NOT_EXIST_ERROR(11312, "账号不存在"),
    ACCOUNTS_CAPTCHA_CODE_ERROR(10802, "验证码不正确"),

    TOKEN_NOT_EXIST_ERR(10002, "凭证验证无法通过，请重新登陆"),


    TOKEN_NOT_ACCESS_ERR(120001, "无访问权限"),
    //Game event enum
    GAME_EVENT_NODE_DELETE_ERROR(10230, "删除节点失败，因为目前节点有下属节点，请先删除下属节点后再删除本节点"),

    DICE_EXCEPTION_COMMIT_ERROR(10010, "骰子异常数据提交失败"),

    DEVICE_FACE_PICTURE_MAX_ERROR(20204, "图片数据过大"),

    PICTURE_RANDOM_GEN_COOKIE_NULL(13051, "图片地址的请求Cookie为空"),

    MACHINE_CODE_EXISTS(14010, "机器码已存在"),

    //解析错误
    DEVICE_DATA_PARSING_ERR(10100, "Device data parsing error"),

    //请求
    DEVICE_TCP_LINK_ERR(10101, "Connection device timeout"),

    DEVICE_REGISTER_AUTHORIZATION_LENGTH_ERR(30001, "The length of data submitted by device registration is incorrect"),

    AUTH_INPUT_DEVICE_CODE_ERR(30500, "Credential registration failed. The custom encoding of the device is incorrect"),

    AUTH_ID_NOT_FOUNT(30501, "Registration credential id not found"),

    //platform-server
    DEVICE_ID_NOT_FOUNT_ERR(30601, "The corresponding device information was not found"),

    METHOD_ARGUMENT_NOT_VALID(10001, "接口请求参数异常"),

    //Ffxiv exception enum
    FFXIV_SCHEME_NODE_NOT_FOUNT_PARENT_ID(40001, "父节点不存在"),

    SUCCESS(0, "Operation Success"),

    ERROR(-1, "System Error"),

    UNKNOWN(-2, "Unknown Exception"),

    UPLOAD(7610, "Upload File Stored Error");

    private final Integer errCode;
    private final String errMsg;

    /**
     * @param errCode 业务模块(2bit) + 异常分类（2bit）+ 自定义编号（2bit）
     * @param errMsg  异常消息
     */
    CommonBaseExceptionEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * find com.ajb.sdk.exception enum by err code
     *
     * @param errCode error code
     * @return com.ajb.sdk.exception enum
     */
    public static CommonBaseExceptionEnum getExceptionEnumByCode(Integer errCode) {
        for (CommonBaseExceptionEnum commonBaseExceptionEnum : CommonBaseExceptionEnum.values()) {
            if (commonBaseExceptionEnum.getErrCode().equals(errCode)) {
                return commonBaseExceptionEnum;
            }
        }
        return UNKNOWN;
    }
}
