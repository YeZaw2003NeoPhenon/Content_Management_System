package com.example.Content_Management_System.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.Content_Management_System.service.userDetailServiceImp;

@Configuration
@EnableWebSecurity
public class webSecurityConfiguration{
	
	@Autowired
	private userDetailServiceImp userDetailServiceImp ;
	
	@Autowired
	public void globalConfiguration( AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailServiceImp);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// builder pattern
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailServiceImp);
		return authenticationProvider;
	}

	@Bean
	public SecurityFilterChain filterChain( HttpSecurity httpSecurity ) throws Exception {
	
			 httpSecurity
						.csrf().disable()
						.authorizeRequests()
<<<<<<< HEAD
						.antMatchers("/login").anonymous()
						.antMatchers("/css/**" , "/js/**" ,"/img/**","/file/**","/uploads/**").permitAll()
						.antMatchers("/users/**").hasRole("users")
						.antMatchers("/admin/**").hasRole("admin")
=======
						.requestMatchers("/login").anonymous()
						.requestMatchers("/css/**" , "/js/**" ,"/img/**","/file/**","/uploads/**").permitAll()
						.requestMatchers("/users/**").hasRole("USERS")
						.requestMatchers("/admin/**").hasRole("ADMIN")
>>>>>>> b320972625253af0747d64de3d8764da4b5418e7
						.anyRequest().authenticated()
						
						.and()
						
						.formLogin()
						.loginPage("/login")
						.loginProcessingUrl("/loginProcess") // as soon as we tuck down to the entire active btn
						.successHandler( new AuthenticationSuccessHandler() {
							
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) throws IOException, ServletException {
								String remember_me = "";
								if( request.getCookies() != null ) {
									for( Cookie cookie : request.getCookies() ) {
										if(cookie.getName().equals("remember-me")) {
											remember_me += cookie.getValue();
										}
									}		
								}
								
								if( remember_me != null ) {
									request.getSession().setAttribute("Custom-Remember-Me",remember_me);
								}
								
								String username = authentication.getName();
								SecurityContextHolder.getContext().setAuthentication(authentication);
								Cookie successCookie = new Cookie("successCookie", "true");
								successCookie.setPath("/posts");
								successCookie.setHttpOnly(true);
								
								response.addCookie(successCookie);
								response.setStatus(HttpServletResponse.SC_OK);
								response.sendRedirect("/posts");
							}
							
						})
						.failureForwardUrl("/login?errorPoppedUp=true")
						.and()
						
						.logout()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout=success")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID" , "remember-me")
						
						.and()
						
						.rememberMe()
						.key("unique")
						.alwaysRemember(true)
						
						.and()
						.sessionManagement()
						.maximumSessions(1)
						.expiredUrl("/login?sessionExpired = true");
			 
						return httpSecurity.build();
	}
	
}
