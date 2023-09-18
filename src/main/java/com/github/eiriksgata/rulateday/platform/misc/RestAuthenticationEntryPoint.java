package com.github.eiriksgata.rulateday.platform.misc;


import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登陆异常处理类
 */

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(
                JSONObject.toJSONString(
                        ResponseBean.error(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR)
        ));
        httpServletResponse.getWriter().flush();
    }
}
