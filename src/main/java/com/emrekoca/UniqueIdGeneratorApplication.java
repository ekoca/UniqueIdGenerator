package com.emrekoca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.emrekoca.generator.unique.UniqueIdGenerator;

@SpringBootApplication
public class UniqueIdGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniqueIdGeneratorApplication.class, args);
	}

	@Bean
	UniqueIdGenerator uniqueIdGenerator() {
		return new UniqueIdGenerator((short) 1024);
	}
}
