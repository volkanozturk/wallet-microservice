package com.leovegas.wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author volkanozturk
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		UserDetails admin = User.withDefaultPasswordEncoder()
				.username("admin")
				.password("password")
				.roles("ADMIN", "USER")
				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http
				.csrf().disable()
				.authorizeRequests(auth -> {
					auth.antMatchers("/api/v1/payments/debit").hasRole("USER");
					auth.antMatchers("/api/v1/payments/credit").hasRole("USER");
					auth.antMatchers("/api/v1/wallets/balance").hasRole("USER");
					auth.antMatchers("/api/v1/payments/*").hasRole("ADMIN");
					auth.antMatchers("/api/v1/wallets/*").hasRole("ADMIN");
					auth.antMatchers("/api/v1/transactions/*").hasRole("ADMIN");
				})
				.httpBasic(withDefaults())
				.build();
	}
}
