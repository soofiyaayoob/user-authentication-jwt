package com.example.demo.ConfigAdmin;

import org.apache.catalina.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.demo.ServiceAdmin.UserdetailServiceAdmin;
import com.example.demo.ServiceAdmin.customHandler;
import com.example.demo.filterAdmin.jwtfilterAdmin;

@Configuration
@EnableWebSecurity
public class configuration {
	@Autowired
	private jwtfilterAdmin jwtfilterAdmin;
	
@Bean
public UserDetailsService detailsService() {
	return new UserdetailServiceAdmin();
}
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new customHandler();
	}
	//private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
		 httpSecurity .csrf(s->s.disable());   
        
     httpSecurity
         .authorizeHttpRequests(req -> req
             .requestMatchers("/register", "/login", "/css/**").permitAll() 
           //  .requestMatchers("/adminpanel/**").hasAuthority("ROLE_ADMIN")
           //  .requestMatchers("/home/**").hasAuthority("ROLE_USER") 
             .anyRequest().authenticated()
         );
   

	       httpSecurity .formLogin().loginProcessingUrl("/perform_login")
	       
	       .loginPage("/login")
	       .successHandler(authenticationSuccessHandler()).
	     
	           
	       failureUrl("/login?error=true")
	       .permitAll();
	       httpSecurity .logout()
	       .logoutSuccessUrl("/login?logout")
	       .invalidateHttpSession(true)
	       .clearAuthentication(true) 
	       .deleteCookies("JSESSIONID","jwt")
	       .logoutUrl("/logout")
	       .logoutSuccessUrl("/login")
	       .permitAll();
	       
	      httpSecurity.headers().cacheControl().disable();
	       httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	     httpSecurity  .addFilterBefore(jwtfilterAdmin, UsernamePasswordAuthenticationFilter.class);
	     

	    return httpSecurity.build();
	 }

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService( detailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
		
	}

}
