package com.github.eiriksgata.rulateday.platform.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String header = "Authorization";
    private Long expire = 604800L;
    private String secret = "bf8390759d67d4ea58383a2103060c50a7852e0eb430a7ff1b0691ad78c31bdf";
    private String prefix = "Bearer ";
}



