package com.pupt.library_tracking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pupt.library_tracking.dto.BorrowingRecordDTO;
import com.pupt.library_tracking.dto.UserDTO;
import com.pupt.library_tracking.model.Book;
import com.pupt.library_tracking.model.BorrowingRecord;
import com.pupt.library_tracking.model.User;
import com.pupt.library_tracking.repository.BookRepository;
import com.pupt.library_tracking.repository.BorrowingRecordRepository;
import com.pupt.library_tracking.repository.UserRepository;
import com.pupt.library_tracking.service.BorrowingRecordService;

@RestController
public class ApiRestController {
	
	UserRepository userRepository;
	BookRepository bookRepository;
	BorrowingRecordRepository borrowingRecordRepository;
	BorrowingRecordService borrowingRecordService;
	
	public ApiRestController(
			UserRepository userRepository,
			BookRepository bookRepository,
			BorrowingRecordRepository borrowingRecordRepository,
			BorrowingRecordService borrowingRecordService) {
		
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.borrowingRecordRepository = borrowingRecordRepository;
		this.borrowingRecordService = borrowingRecordService;
	}
	
	@GetMapping("/admin/api/users")
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List <UserDTO> usersDto = users.stream()
			.map(user -> new UserDTO(user.getId(), user.getUsername()))
			.collect(Collectors.toList());
		return usersDto;
	}
	
	@GetMapping("/api/records")
	public List<BorrowingRecordDTO> getAllBorrowingRecords() {
        return borrowingRecordService.findAllBorrowingRecords().stream()
        		.map(BorrowingRecordDTO::new)
        		.toList();
	}

	@GetMapping("/api/books")
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	@GetMapping("/api/records/date")
	public List<BorrowingRecordDTO> getRecordsByDate (@RequestParam int month, @RequestParam int year) {
		return borrowingRecordRepository.findRecordsByMonthAndYear(month, year).stream()
				.map(BorrowingRecordDTO::new)
				.toList();
	}
}
