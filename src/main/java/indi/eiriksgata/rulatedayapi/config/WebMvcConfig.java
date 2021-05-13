package indi.eiriksgata.rulatedayapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.anjubao.deviceapi.config
 * date: 2021/3/24
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public InterceptorConfig needLoginInterceptor() {
        return new InterceptorConfig();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(needLoginInterceptor())
                .addPathPatterns("/apiv1/**");


    }

}
