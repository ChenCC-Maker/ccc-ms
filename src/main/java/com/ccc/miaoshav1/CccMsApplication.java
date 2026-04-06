package com.ccc.miaoshav1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ccc.miaoshav1.dao")
public class CccMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CccMsApplication.class, args);
	}

}
