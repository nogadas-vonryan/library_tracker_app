package com.pupt.library_tracking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private int id;
	private String username;

	public UserDTO(int id, String username) {
		this.id = id;
		this.username = username;
	}
}
