package com.example.jreg0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Jreg0Application {

	public static void main(String[] args) {
		SpringApplication.run(Jreg0Application.class, args);
	}

	/**
	 * APIヘルスチェック用
	 *
	 * @return OK
	 * */
	@GetMapping("/")
	public String HealthCheck(){
		return "Ok";
	}
}
