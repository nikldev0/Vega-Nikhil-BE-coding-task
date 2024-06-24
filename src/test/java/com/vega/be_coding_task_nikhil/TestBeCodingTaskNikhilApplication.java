package com.vega.be_coding_task_nikhil;

import org.springframework.boot.SpringApplication;

public class TestBeCodingTaskNikhilApplication {

	public static void main(String[] args) {
		SpringApplication.from(BeCodingTaskNikhilApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
