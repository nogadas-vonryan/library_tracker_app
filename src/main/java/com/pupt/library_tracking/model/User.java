package com.pupt.library_tracking.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String studentNumber;
	private String firstName;
	private String lastName;
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Collection<String> roles;
	
	protected User() { }
	
	public User(String studentNumber, String firstName, String lastName, String password) {
		this.studentNumber = studentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.roles = List.of("USER");
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> (GrantedAuthority) () -> role).toList();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return studentNumber;
	}
}
