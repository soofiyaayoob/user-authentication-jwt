package com.example.demo.ServiceAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.ModelAdmin.userOfAdmin;
import com.example.demo.ModelAdmin.userPrincpleAdmin;
import com.example.demo.RepoAdmin.repositoryAdmin;
@Service
public class UserdetailServiceAdmin implements UserDetailsService{
	
	@Autowired
	repositoryAdmin repositoryAdmin;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		userOfAdmin user=repositoryAdmin.findByUsername(username);
		if(user==null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException("oh ithink you are not existed");
			
		}
		return new userPrincpleAdmin(user);
	}
	
	

}
