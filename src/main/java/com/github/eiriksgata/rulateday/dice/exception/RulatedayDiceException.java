package com.github.eiriksgata.rulateday.dice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
//改成必须捕捉异样并处理，如果不符合指令的全部抛出异常，用于用户处理
public class RulatedayDiceException extends Exception {

    private static final long serialVersionUID = -6301630219349537830L;

    private Integer errCode;
    private String errMsg;

    public RulatedayDiceException(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public RulatedayDiceException(String errMsg) {
        this.errCode = RulatedayDiceExceptionEnum.UNKNOWN.getErrCode();
        this.errMsg = errMsg;
    }

    public RulatedayDiceException(RulatedayDiceExceptionEnum exceptionEnum) {
        this.errCode = exceptionEnum.getErrCode();
        this.errMsg = exceptionEnum.getErrMsg();
    }

    public RulatedayDiceException(RulatedayDiceExceptionEnum exceptionEnum, Throwable cause) {
        super(cause);
        this.errCode = exceptionEnum.getErrCode();
        this.errMsg = exceptionEnum.getErrMsg();
    }


}
