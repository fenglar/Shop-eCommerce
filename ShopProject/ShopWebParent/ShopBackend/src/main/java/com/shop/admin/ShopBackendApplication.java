package com.shop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shop.common.entity", "com.shop.admin.user"})
public class ShopBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopBackendApplication.class, args);
	}

}
