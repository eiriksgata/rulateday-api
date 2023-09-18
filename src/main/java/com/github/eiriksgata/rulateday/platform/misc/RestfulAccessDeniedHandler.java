package com.github.eiriksgata.rulateday.platform.misc;

import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足异常处理类
 */

public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.getWriter().write(JSONObject.toJSONString(
                ResponseBean.error(CommonBaseExceptionEnum.TOKEN_NOT_ACCESS_ERR)
        ));
    }
}
