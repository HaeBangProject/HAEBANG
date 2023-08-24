package com.haebang.haebang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.haebang.haebang.repository")
@SpringBootApplication
public class HaebangApplication {
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
		// or VM option 추가 -Dcom.amazonaws.sdk.disableEc2Metadata=true
	}
	public static void main(String[] args) {
		SpringApplication.run(HaebangApplication.class, args);
	}

}
