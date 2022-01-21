package indi.eiriksgata.rulatedayapi.exception;

import indi.eiriksgata.rulatedayapi.utils.ExceptionUtils;
import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * default exception handler(框架异常)
     *
     * @param ex framework exception
     * @return framework error
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseBean<String> defaultErrorHandler(Exception ex) {
        // simple print stack trace
        ex.printStackTrace();
        return ResponseBean.error(ex.getMessage());
    }

    /**
     * validation exception(接口参数校验异常)
     *
     * @param request request
     * @param ex      validation exception
     * @return validation error
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseBean<String> handleMethodArgumentNotValidException(
            HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("Invalid Request:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append("[").append(fieldError.getField()).append("]");
            errorMessage.append(fieldError.getDefaultMessage()).append(";");
        }
        // simple print stack trace
        ex.printStackTrace();
        return ResponseBean.error(CommonBaseExceptionEnum.METHOD_ARGUMENT_NOT_VALID, errorMessage.toString());
    }

    /**
     * common exception handler(业务异常)
     *
     * @param request request
     * @param ex      business exception
     * @return business error
     */
    @ExceptionHandler(value = CommonBaseException.class)
    @ResponseBody
    public ResponseBean<?> handleCommonException(HttpServletRequest request, CommonBaseException ex) {
        ex.printStackTrace();
        return ResponseBean.error(CommonBaseExceptionEnum.getExceptionEnumByCode(ex.getErrCode()));
    }

    @ExceptionHandler(value = NumberFormatException.class)
    @ResponseBody
    public ResponseBean<?> handleNumberFormatException(NumberFormatException ex) {
        ex.printStackTrace();
        return ResponseBean.error(ExceptionUtils.getExceptionAllInfo(ex));
    }

    @ExceptionHandler(value = BindValidationException.class)
    @ResponseBody
    public ResponseBean<?> handleValidationException(BindValidationException ex) {
        ex.printStackTrace();
        return ResponseBean.error(ExceptionUtils.getExceptionAllInfo(ex));
    }


    /** add more... */

}
