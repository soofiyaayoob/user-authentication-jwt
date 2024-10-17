package com.example.demo.ModelAdmin;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class userPrincpleAdmin implements UserDetails {
	
	private userOfAdmin userOfAdmin;

	public userPrincpleAdmin(userOfAdmin userOfAdmin) {
	
		this.userOfAdmin = userOfAdmin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(userOfAdmin.getRole().name()));
	}
	

	@Override
	public String getPassword() {
		
		return userOfAdmin.getPassword();
	}

	@Override
	public String getUsername() {
		
		return userOfAdmin.getUsername();
	}
	

}
