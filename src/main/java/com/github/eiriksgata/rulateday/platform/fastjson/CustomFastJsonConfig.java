package com.github.eiriksgata.rulateday.platform.fastjson;

import com.alibaba.fastjson2.filter.ValueFilter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFastJsonConfig {
//    @Bean
//    FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//        //1.需要定义一个convert转换消息的对象
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//
//        //2.添加fastJson的配置信息
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        //3.设置id字段为字符串
//        fastJsonConfig.setWriterFilters((ValueFilter) (object, name, value) -> {
//            //如果Long型超出了js的number最大范围，则用string表示
//            if (name.matches("[iI][d]")) {
//                return value + "";
//            }
//            return value;
//        });
//
//        //4.在convert中添加配置信息.
//        converter.setFastJsonConfig(fastJsonConfig);
//        return converter;
//    }
}
