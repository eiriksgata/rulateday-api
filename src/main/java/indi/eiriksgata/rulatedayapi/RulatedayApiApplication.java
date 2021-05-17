package indi.eiriksgata.rulatedayapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "indi.eiriksgata.rulateday.mapper")
public class RulatedayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RulatedayApiApplication.class, args);
	}

}
