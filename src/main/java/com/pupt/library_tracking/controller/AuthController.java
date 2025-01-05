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
            return "redirect:/user/books";
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
			@RequestParam String referenceNumber,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String password,
			@RequestParam String confirmPassword) {
		
		if (!password.equals(confirmPassword)) {
			System.out.println("Passwords do not match");
			return "redirect:/register?error=PasswordMismatch";
		}
		
		if (userRepository.findByReferenceNumber(referenceNumber).isPresent()) {
			System.out.println("User already exists");
			return "redirect:/register?error=UserExists";
		}
		
		User user = new User(
				referenceNumber,
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
		User user = userRepository.findByReferenceNumber(principal.getName()).get();
		
		if (user.getRoles().contains("ROLE_ADMIN")) {
			return "redirect:/admin/books";
		} else if (user.getRoles().contains("ROLE_USER")) {
			return "redirect:/user/books";
		}
		
		return "redirect:/login?error=true";
	}
}
