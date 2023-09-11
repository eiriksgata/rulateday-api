package com.github.eiriksgata.rulateday.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.ajb.pushserver.conf
 * date: 2020/12/10
 **/

@Configuration
public class WebPathConfig implements WebMvcConfigurer {

    @Autowired
    RequestInterceptor requestInterceptor;

    /*
     * 文件默认存储位置
     */
    public static String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/resources";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:" + DEFAULT_FILE_PATH + "/");
    }

    /**
     * 拦截器路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registration) {
        registration.addInterceptor(requestInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/authentication");

    }


}
