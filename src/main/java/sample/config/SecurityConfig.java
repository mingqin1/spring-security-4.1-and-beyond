/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Rob Winch
 * @author Joe Grandja
 */
//@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		        .csrf().disable()
                        /*
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
                         */       
			.authorizeRequests()
				.antMatchers("/","/assets/**", "/webjars/**").permitAll()
				//.antMatchers("/users/{userId}").access("@authz.check(#userId,principal)")
				//.mvcMatchers("/admin").denyAll()
				.anyRequest().authenticated()
				.and()
			.httpBasic()
				.and()
			.formLogin()
				.loginPage("/")
				.permitAll()
				.and()
			.headers().frameOptions().disable()
				  .contentSecurityPolicy("default-src 'self' " +
						"https://ajax.googleapis.com " +
						"https://cdnjs.cloudflare.com; " +
						"style-src 'self'").reportOnly();
                                
	}
	// @formatter:on

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}