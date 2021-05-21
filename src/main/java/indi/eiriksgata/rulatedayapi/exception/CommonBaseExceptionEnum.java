package indi.eiriksgata.rulatedayapi.exception;

import lombok.Getter;

/**
 * 统一异常码
 *
 * @author Snake
 * @date 2019/11/14
 */
@Getter
public enum CommonBaseExceptionEnum {

    DICE_EXCEPTION_COMMIT_ERROR(10010, "骰子异常数据提交失败"),

    DEVICE_FACE_PICTURE_MAX_ERROR(20204, "图片数据过大"),

    //解析错误
    DEVICE_DATA_PARSING_ERR(10100, "Device data parsing error"),

    //请求
    DEVICE_TCP_LINK_ERR(10101, "Connection device timeout"),

    DEVICE_REGISTER_AUTHORIZATION_LENGTH_ERR(30001, "The length of data submitted by device registration is incorrect"),

    AUTH_INPUT_DIEVCE_CODE_ERR(30500, "Credential registration failed. The custom encoding of the device is incorrect"),

    AUTH_ID_NOT_FOUNT(30501, "Registration credential id not found"),

    AUTH_COMMNUTITY_NUMBER_NOT_FOUNT(30502, "Cell number not found"),

    //plateform-server
    DEVICE_ID_NOT_FOUNT_ERR(30601, "The corresponding device information was not found"),

    SUCCESS(0, "Operation Success"),

    ERROR(-1, "System Error"),

    UNKNOWN(-2, "Unknown Exception"),

    UPLOAD(1, "Upload File Stored Error");

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
