package com.wemakeitwork.allenvooreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@SpringBootApplication
public class AllenVoorEenApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllenVoorEenApplication.class, args);
	}
}
