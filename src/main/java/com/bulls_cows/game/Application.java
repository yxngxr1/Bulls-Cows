package com.bulls_cows.game;

import com.bulls_cows.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
	@Autowired
	UserService service;
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

    }

}
