package com.juliuskrah.angular;

import java.util.UUID;import lombok.Data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@SpringBootApplication
public class Application extends WebSecurityConfigurerAdapter {

	@RequestMapping("/")
	@CrossOrigin(origins="*", maxAge=3600, 
		allowedHeaders = { "x-auth-token", "x-requested-with", "x-xsrf-token" })
	public Message home() {
		return new Message("Hello World");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * HttpSessionStrategy that looks in the header ("X-Auth-Token" by default) 
	 * instead of the default (cookie named "JSESSIONID")
	 */
	@Bean
	HeaderHttpSessionStrategy sessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests().anyRequest().authenticated();
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
