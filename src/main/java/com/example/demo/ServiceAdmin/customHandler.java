package com.example.demo.ServiceAdmin;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class customHandler implements AuthenticationSuccessHandler{
	
	

 String redirectUrl=null;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		
	    String jwt = jwtServiceAdmin.generateToken(authentication.getName());
	    System.out.println(jwt);
	    
	   
	    ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
	            .httpOnly(true)
	            .path("/")
	            .maxAge(7 * 24 * 60 * 60)
	            .secure(true)
	            .build();
	    response.addHeader("Set-Cookie", cookie.toString());
		 Collection<? extends GrantedAuthority> authorties =authentication.getAuthorities();
	  if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
          redirectUrl = "/adminpanel";
      } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
          redirectUrl = "/home";
      }

     response.sendRedirect(redirectUrl);
     
		
    
      		//			   for (GrantedAuthority authority : authorties) {
//      		            if (authority.getAuthority().equals("ROLE_ADMIN")) {
//      		                response.sendRedirect("/admin/panel");  // Redirect to admin panel
//      		                return;
//      		            }
//      		        }
//      		        
//      		        // If no ROLE_ADMIN, default redirect to user home
//      		        response.sendRedirect("/user/home");  // Default redirect for regular users
//      		    }
	}

}
