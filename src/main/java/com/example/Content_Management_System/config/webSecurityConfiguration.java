package com.example.Content_Management_System.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties.ConfigureAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
	public SecurityFilterChain filterChain( HttpSecurity httpSecurity ) throws Exception {
	
			httpSecurity
						.csrf().disable()
						.authorizeRequests()
						.requestMatchers("/login").anonymous()
						.requestMatchers("/css/**" , "/js/**" ,"/img/**","/file/**","/uploads/**").permitAll()
						.requestMatchers("/users/**").hasRole("users")
						.requestMatchers("/admin/**").hasRole("admin")
						.anyRequest().authenticated()
						.and()
						.formLogin()
						.loginPage("/login")
						.loginProcessingUrl("/loginProcess") // as soon as we tuck down to the entire active btn
						.defaultSuccessUrl("/posts")
						.failureUrl("/login?error=true")
						.and()
						.logout()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout=success")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID" , "remember-me");
			
				return httpSecurity.build();
	}
	
}
