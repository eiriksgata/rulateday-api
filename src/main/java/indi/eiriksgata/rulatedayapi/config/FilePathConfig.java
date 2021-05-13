package indi.eiriksgata.rulatedayapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author: create by Keith
 * version: v1.0
 * description: com.ajb.pushserver.conf
 * date: 2020/12/10
 **/

@Configuration
public class FilePathConfig implements WebMvcConfigurer {

    /*
     * 文件默认存储位置
     */
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "\\media";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/media/**").addResourceLocations("file:\\" + DEFAULT_FILE_PATH + "\\\\");
    }

}
