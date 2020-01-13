package com.wemakeitwork.allenvooreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@SpringBootApplication
public class AllenVoorEenApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AllenVoorEenApplication.class, args);
		System.setProperty("server.servlet.context-path", "/allenvooreen");
	}
}
