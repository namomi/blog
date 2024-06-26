// package com.namomi.blog.config;
//
// import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.stereotype.Service;
//
// import com.namomi.blog.service.UserDetailService;
//
// import lombok.RequiredArgsConstructor;
//
// 세션 기반

// @RequiredArgsConstructor
// @Service
// public class WebSecurityConfig {
//
// 	private final UserDetailService userService;
//
// 	@Bean
// 	public WebSecurityCustomizer configure() {
// 		return (web) -> web.ignoring()
// 			.requestMatchers(toH2Console())
// 			.requestMatchers("/static/**");
// 	}
//
// 	@Bean
// 	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// 		http
// 			.authorizeHttpRequests(authorize -> authorize
// 				.requestMatchers("/login", "/signup", "/user").permitAll()
// 				.anyRequest().authenticated()
// 			)
// 			.formLogin(form -> form
// 				.loginPage("/login")
// 				.defaultSuccessUrl("/articles")
// 			)
// 			.logout(logout -> logout
// 				.logoutSuccessUrl("/login")
// 				.invalidateHttpSession(true)
// 			)
// 			.csrf(AbstractHttpConfigurer::disable);
//
// 		return http.build();
// 	}
//
// 	@Bean
// 	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
// 		UserDetailService userDetailService) throws Exception {
// 		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
// 		authManagerBuilder
// 			.userDetailsService(userDetailService)
// 			.passwordEncoder(bCryptPasswordEncoder);
// 		return authManagerBuilder.build();
// 	}
//
// 	@Bean
// 	public BCryptPasswordEncoder bCryptPasswordEncoder() {
// 		return new BCryptPasswordEncoder();
// 	}
// }
