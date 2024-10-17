package com.example.demo.ServiceAdmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.ModelAdmin.userOfAdmin;
import com.example.demo.ModelAdmin.userOfAdmin.Role;
import com.example.demo.RepoAdmin.repositoryAdmin;



@Service
public class serviceLayerAdmin {
	
	@Autowired
	repositoryAdmin repositoryAdmin;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	 

	    public userOfAdmin registerUser(userOfAdmin user) {
	   
	    	    if (user.getPassword() != null ) {
	    	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	    	        //user.setRole(Role.ROLE_ADMIN);
	    	    } else {
	    	      //user.setPassword(existing);
	    	        throw new IllegalArgumentException("Password cannot be empty");
	    	    }
	    	    return repositoryAdmin.save(user);
	    }

	    public List<userOfAdmin> getAllUsers() {
	        return repositoryAdmin.findAll();
	    }

	    public userOfAdmin getUserById(Long id) {
	        return repositoryAdmin.findById(id).orElse(null);
	    }

	    public userOfAdmin updateUser(userOfAdmin user) {
	    	
	
	    	Optional<userOfAdmin> existingUserOptional = repositoryAdmin.findById(user.getId());

	      
	        if (!existingUserOptional.isPresent()) {
	            throw new IllegalArgumentException("User not found"); 
	        }

	        userOfAdmin existingUser = existingUserOptional.get(); 

	       
	        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
	           
	            user.setPassword(passwordEncoder.encode(user.getPassword()));
	        } else {
	           
	            user.setPassword(existingUser.getPassword());
	        }

	       

	        return repositoryAdmin.save(user);
	    }

	    public void deleteUser(Long id) {
	        repositoryAdmin.deleteById(id);
	    }
	    public boolean verify(userOfAdmin user) {
			try {
		       
		        Authentication authentication = 
		            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		        
		     
		        Authentication result = authenticationManager.authenticate(authentication);
		        
		       
		        return result.isAuthenticated();
		    } catch (AuthenticationException e) {
		        
		        return false;
		    }
		 }

	}

