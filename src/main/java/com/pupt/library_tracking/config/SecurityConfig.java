package com.pupt.library_tracking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.pupt.library_tracking.handler.MyLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/about", "/login", "/register", "/uploads/**", "/stylesheet/**", "/assets/**", "/js/**").permitAll()
						.requestMatchers("/user/**").hasRole("USER")
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/redirect", true)
						.failureUrl("/login?error=true").permitAll())
				.logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler()).logoutUrl("/logout").logoutSuccessUrl("/login").permitAll())
				.build();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new MyLogoutSuccessHandler();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
