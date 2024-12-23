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

import com.example.Content_Management_System.security.user.userDetailServiceImp;

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
						.antMatchers("/login").anonymous()
						.antMatchers("/css/**" , "/js/**" ,"/img/**","/file/**","/uploads/**").permitAll()
						.antMatchers("/users/**").hasRole("USER")
						.antMatchers("/admin/**").hasRole("ADMIN")
						.anyRequest().authenticated()
						.and()
						
						.formLogin()
						.loginPage("/login")
						.loginProcessingUrl("/loginProcess") // as soon as we tuck down to the entire active btn
						.defaultSuccessUrl("/posts")
						.failureForwardUrl("/login?errorPoppedUp=true")
						.and()
						
						.rememberMe()
						.key("unique")
						.rememberMeCookieName("remember-me")
						.rememberMeParameter("remember-me")
						.tokenValiditySeconds((int)TimeUnit.SECONDS.toDays(21))
						.and()
						
						.logout()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout=success")
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID" , "remember-me")
						.and()
						
						.sessionManagement()
						.maximumSessions(1)
						.expiredUrl("/login?sessionExpired = true");
			 
						return httpSecurity.build();
	}
	
	
}
