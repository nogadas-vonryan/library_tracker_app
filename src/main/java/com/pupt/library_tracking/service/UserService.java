package com.pupt.library_tracking.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pupt.library_tracking.model.User;

@Service
public class UserService {
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return (User) authentication.getPrincipal();
		}
		return null;
	}
}
