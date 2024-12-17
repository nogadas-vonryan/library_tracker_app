package com.pupt.library_tracking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pupt.library_tracking.model.Book;
import com.pupt.library_tracking.repository.BookRepository;

@RestController
public class AdminRestController {
	BookRepository bookRepository;
	
	public AdminRestController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@GetMapping("/api/books")
	public List<Book> getRecords() {
		return bookRepository.findAll();
	}
}
