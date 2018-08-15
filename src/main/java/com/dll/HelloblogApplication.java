package com.dll;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dll.dao")
public class HelloblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloblogApplication.class, args);
	}
	
}
