package com.haebang.haebang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.haebang.haebang.repository")
@SpringBootApplication
public class HaebangApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaebangApplication.class, args);
	}

}
