package com.juliuskrah.angular;

import java.util.UUID;
import lombok.Data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application extends WebSecurityConfigurerAdapter {

	@RequestMapping("/")
	public Message home() {
		return new Message("Hello World");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable();
		http.authorizeRequests().anyRequest().authenticated();
	}

	@Data
	class Message {
		private String id = UUID.randomUUID().toString();
		private String content;

		public Message(String content) {
			this.content = content;
		}
		// ... getters and setters and default constructor
	}

}
