package indi.eiriksgata.rulatedayapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.ajb.config
 * date: 2021/3/24
 **/
@Configuration
public class InterceptorConfig implements HandlerInterceptor {

    //方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (ProxyConfig.loadConfigBean.getToken().equals(request.getHeader("token"))) {
//            return true;
//        } else {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            response.getWriter().write("{\"code\":401,\"message\":\"Token不正确\"}");
//            response.setStatus(401);
//            return false;
//
//        }

        return true;
    }


}
