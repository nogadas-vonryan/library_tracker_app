package com.pupt.library_tracking.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pupt.library_tracking.model.Book;
import com.pupt.library_tracking.repository.BookRepository;

@Controller
public class UserBookController {
	
	private BookRepository bookRepository;
	
	public UserBookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping("/user/books")
	public String showBooks(Model model) {
		List<Book> books = bookRepository.findAll();
		
		model.addAttribute("books", books);	
		return "book_list";
	}

	@PostMapping("/user/books")
	public String addBook(@RequestParam String title, @RequestParam String author, @RequestParam String category) {
		bookRepository.save(new Book(title, author, category));
		return "redirect:/books";
	}
}
