package com.eCommerce.MyECommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MyECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyECommerceApplication.class, args);
	}

}
