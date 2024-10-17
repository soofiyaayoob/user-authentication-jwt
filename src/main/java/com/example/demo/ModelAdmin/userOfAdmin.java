package com.example.demo.ModelAdmin;

import javax.management.relation.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="jwtTokenUsingAdmin")
public class userOfAdmin {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String username;
	    private String email;
	    private String password;
	    private String gender;

	    @Enumerated(EnumType.STRING)
	    private Role role = Role.ROLE_USER; // Default role set to USER
	   
		public enum Role {
		    ROLE_USER,
		    ROLE_ADMIN
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		public userOfAdmin(Long id, String username, String email, String password, String gender, Role role) {
			super();
			this.id = id;
			this.username = username;
			this.email = email;
			this.password = password;
			this.gender = gender;
			this.role = role;
		}
		public userOfAdmin() {
			
		}
	    
	    
}
