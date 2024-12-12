package com.pupt.library_tracking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pupt.library_tracking.exception.BookNotFoundException;
import com.pupt.library_tracking.exception.UserNotFoundException;
import com.pupt.library_tracking.model.Book;
import com.pupt.library_tracking.model.BorrowingRecord;
import com.pupt.library_tracking.model.User;
import com.pupt.library_tracking.repository.BookRepository;
import com.pupt.library_tracking.repository.BorrowingRecordRepository;
import com.pupt.library_tracking.repository.UserRepository;

@Service
public class BorrowingRecordService {
	private BorrowingRecordRepository borrowingRecordRepository;
	private BookRepository bookRepository;
	private UserRepository userRepository;
	
	public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, 
								  BookRepository bookRepository,
								  UserRepository userRepository) {
		this.borrowingRecordRepository = borrowingRecordRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}
	
	public void saveBorrowingRecord(String username, String bookTitle) throws UserNotFoundException, BookNotFoundException {
		User user = userRepository.findByUsername(username).get();
		Book book = bookRepository.findByTitle(bookTitle);
		
		if (user == null) {
			throw new UserNotFoundException("User not found");
		}
		
		if (book == null) {
			throw new BookNotFoundException("Book not found");
		}
		
		try {
			borrowingRecordRepository.save(new BorrowingRecord(user, book));
		} catch (Exception e) {
		    throw new RuntimeException("Error saving borrowing record");
		}
		
		System.out.println("Added order: [" + book.getTitle() + "] [" + user.getUsername() + "]");
	}
	
	public List<BorrowingRecord> findAllBorrowingRecords() {
		return borrowingRecordRepository.findAll();
	}
}
