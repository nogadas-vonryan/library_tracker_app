package com.pupt.library_tracking.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
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
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String author;
	private String category;
	private String imageUrl;
	
	@CreatedDate
	private LocalDateTime dateCreated;

	protected Book() { }
	
	public Book(String title, String author, String category) {
		this.title = title;
		this.author = author;
		this.category = category;
	}

	public Book(String title, String author, String category, String imageUrl) {
		this.title = title;
		this.author = author;
		this.category = category;
		this.imageUrl = imageUrl;
	}
}
