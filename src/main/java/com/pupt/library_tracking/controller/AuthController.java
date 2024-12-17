package com.pupt.library_tracking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pupt.library_tracking.model.User;
import com.pupt.library_tracking.repository.UserRepository;

@Controller
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String login(Principal principal) {
		if(principal != null) {
            System.out.println("User already logged in");
            return "redirect:/dashboard";
		}
		System.out.println("Opened login page");
		return "login";
	}
	
	@GetMapping("/register")
	public String displayRegisterPage() {
		System.out.println("Opened register page");
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(
			@RequestParam String studentNumber,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String password) {
		User user = new User(
				studentNumber,
				firstName,
				lastName,
				passwordEncoder.encode(password)
			);
	    
		userRepository.save(user);
		System.out.println("User registered");
		return "login";
	}
	
	
	@GetMapping("/")
	public String home(Principal principal) {
		if (principal != null) {
			System.out.println("User already logged in");
			return "redirect:/redirect";
		}
		
		return "index";
	}

	@GetMapping("/redirect")
	public String authRedirect(Principal principal) {
		User user = userRepository.findByStudentNumber(principal.getName()).get();
		
		if (user.getRoles().contains("ADMIN")) {
			return "redirect:/admin/books";
		}
		
		return "redirect:/user/books";
	}
}
