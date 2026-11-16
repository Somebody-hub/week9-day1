package com.example.week9_day1;

import org.springframework.boot.SpringApplication;

public class TestWeek9Day1Application {

	public static void main(String[] args) {
		SpringApplication.from(Week9Day1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
