package com.pupt.library_tracking.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pupt.library_tracking.model.Book;
import com.pupt.library_tracking.repository.BookRepository;

@Controller
public class AdminBookController {
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";
	private BookRepository bookRepository;
	
	public AdminBookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping("/admin/books")
	public String showBooks(Model model) {
		List<Book> books = bookRepository.findAll();
		
		model.addAttribute("books", books);	
		return "admin-books";
	}
	
	@GetMapping("/admin/books/add")
	public String addBookPage() {
		return "admin-books-add";
	}
	
	@PostMapping("/admin/books/add")
	public String addBook(
			@RequestParam String title, 
			@RequestParam String author, 
			@RequestParam String category,
			@RequestParam MultipartFile bookCover) {
		
		try {
            String fileName = saveImage(bookCover);
            bookRepository.save(new Book(title, author, category, fileName));
            System.out.println(fileName);
            return "redirect:/admin/books";
        } catch (IOException e) {
            return "redirect:/admin/books/add?error=true";
        }
	}
	
	private String saveImage(MultipartFile file) throws IOException {
		if (file == null) {
			return "";
        }
		
		Path uploadPath = Paths.get(UPLOAD_DIR);
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        String contentType = file.getContentType();
        
		if (!contentType.startsWith("image")) {
			return "";
		}
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
	
	@GetMapping("/admin/books/{id}")
	public String showBookDetail(Model model, @PathVariable int id) {
		Book book = bookRepository.findById(id);
		model.addAttribute("book", book);
		System.out.println(book);
		return "admin-books-edit";
	}
	
	@PostMapping("/admin/books/{id}")
	public String editBook(@PathVariable int id, @RequestParam String title, @RequestParam String author,
			@RequestParam String category, @RequestParam MultipartFile bookCover) {

		Book book = bookRepository.findById(id);

		try {
			String fileName = saveImage(bookCover);
			book.setTitle(title);
			book.setAuthor(author);
			book.setCategory(category);
			if(!fileName.isEmpty()) book.setImageUrl(fileName);
			bookRepository.save(book);
			
			return "redirect:/admin/books";
		} catch (IOException e) {
			return "redirect:/admin/books/" + id + "?error=true";
		}
	}
}
