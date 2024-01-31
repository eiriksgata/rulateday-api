package com.github.eiriksgata.rulateday.platform;

import com.github.eiriksgata.rulateday.platform.dice.init.LoadDatabaseFile;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.github.eiriksgata.rulateday.platform.mapper"})
@ComponentScan(basePackages = {
        "com.github.eiriksgata.rulateday.platform"
})
public class RulatedayApiApplication {

    public static void main(String[] args) {
        LoadDatabaseFile.init();
        SpringApplication.run(RulatedayApiApplication.class, args);
    }

}
