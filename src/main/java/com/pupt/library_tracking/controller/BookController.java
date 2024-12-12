package com.pupt.library_tracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pupt.library_tracking.model.Book;
import com.pupt.library_tracking.repository.BookRepository;

@Controller
public class BookController {
	
	private BookRepository bookRepository;
	
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping("/books")
	public String showBooks(Model model) {
		List<Book> books = bookRepository.findAll();
		
		model.addAttribute("books", books);	
		return "book_list";
	}
	
	@PostMapping("/books")
	public String addBook(@RequestParam String title, @RequestParam String author) {
		bookRepository.save(new Book(title, author));
		return "redirect:/books";
	}
}
