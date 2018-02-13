package com.juliuskrah.angular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableOAuth2Sso
@EnableZuulProxy
@SpringBootApplication
public class Application extends WebSecurityConfigurerAdapter {

	// @GetMapping(value = "/{path:[^\\.]*}")
	public String redirect() {
		return "forward:/";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	protected OAuth2RestTemplate OAuth2RestTemplate(OAuth2ProtectedResourceDetails resource,
			OAuth2ClientContext context) {
		return new OAuth2RestTemplate(resource, context);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.logout().logoutSuccessUrl("/").and().authorizeRequests().antMatchers("/index.html", "/app.html", "/")
				.permitAll().anyRequest().authenticated().and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

}
