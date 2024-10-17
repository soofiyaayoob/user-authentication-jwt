package com.example.demo.CondrollerAdmin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.ModelAdmin.userOfAdmin;
import com.example.demo.ModelAdmin.userPrincpleAdmin;
import com.example.demo.ServiceAdmin.jwtServiceAdmin;
import com.example.demo.ServiceAdmin.serviceLayerAdmin;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@Controller

public class homecondroller {
@Autowired
serviceLayerAdmin serviceLayerAdmin;




@GetMapping("/register")
public String showRegistrationForm(Model model) {
    model.addAttribute("user", new userOfAdmin());
    return "register";
}

@PostMapping("/register")
public String registerUser(@ModelAttribute userOfAdmin user) {
    serviceLayerAdmin.registerUser(user);
    return "redirect:/login"; 
}

@GetMapping("/adminpanel")
public String listUsers(Model model) {
    List<userOfAdmin> users = serviceLayerAdmin.getAllUsers();
    model.addAttribute("users", users);
 // SecurityContextHolder.getContext().getAuthentication();
    return "adminpanel";
}

@PostMapping("/admin/delete/{id}")
public String deleteUser(@PathVariable Long id) {
    serviceLayerAdmin.deleteUser(id);
    return "redirect:/adminpanel";
}

@GetMapping("/admin/update/{id}")
public String showUpdateForm(@PathVariable Long id, Model model) {
    userOfAdmin users = serviceLayerAdmin.getUserById(id);
    model.addAttribute("users", users);
    return "update"; 
}


@PostMapping("/admin/update/{id}")
public String updateUser(@PathVariable Long id, @ModelAttribute userOfAdmin users) {
  
    users.setId(id);
    try {
        serviceLayerAdmin.updateUser(users);
        System.out.println("User updated successfully");
    } catch (Exception e) {
        System.err.println("Error updating user: " + e.getMessage());
        // Optionally, you can return an error page or a different redirect
        return "redirect:/error"; // Redirect to an error page
    }
    return "redirect:/adminpanel"; 
}


//@GetMapping("/logout-success")
//public String logoutSuccess() {
//    return "redirect:/login";
//}


@GetMapping("/home")
public String home(Model model, Principal principal) {
    if (principal != null) {
        String username = principal.getName(); 

        model.addAttribute("username", username); 
    }
    return "home"; 
}

@GetMapping("/login")
public String loginPage(Authentication authentication) {
	  if (authentication != null) {
         
          return "redirect:/home"; 
      }
   
      return "login"; 
  }
  
}
