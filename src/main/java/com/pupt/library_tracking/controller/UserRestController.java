package com.pupt.library_tracking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pupt.library_tracking.dto.UserDTO;
import com.pupt.library_tracking.model.User;
import com.pupt.library_tracking.repository.UserRepository;

@RestController
public class UserRestController {
	
	UserRepository userRepository;
	
	public UserRestController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/admin/api/users")
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List <UserDTO> usersDto = users.stream()
			.map(user -> new UserDTO(user.getId(), user.getUsername()))
			.collect(Collectors.toList());
		return usersDto;
	}
}
