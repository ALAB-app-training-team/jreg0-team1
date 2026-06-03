package com.example.jreg0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Jreg0Application {

	public static void main(String[] args) {
		SpringApplication.run(Jreg0Application.class, args);
	}

	@Autowired
	private TrainService _service;

	@GetMapping("/")
	public String Hello(){
		List<TrainEntity> trains = _service.getAll();
		return trains.getFirst().getId() + trains.getFirst().getTrain_name() + "やあやあ！";
	}
}
