package indi.eiriksgata.rulateday.api.config;

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
    public static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "\\resources";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:\\" + DEFAULT_FILE_PATH + "\\\\");
    }

}
