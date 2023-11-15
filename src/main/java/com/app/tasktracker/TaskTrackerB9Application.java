package com.app.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class TaskTrackerB9Application {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerB9Application.class, args);
		System.out.println("Welcome to Java-9's Task Tracker project!");
	}
	@GetMapping
	public String greetings(){
		return "index";
	}

}
