package com.pupt.library_tracking.service;

import java.util.List;

import org.springframework.data.domain.Sort;
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
	
	public void saveBorrowingRecord(String studentNumber, String bookTitle, String borrowDate, String returnDate) throws UserNotFoundException, BookNotFoundException {
		User user = userRepository.findByReferenceNumber(studentNumber).get();
		Book book = bookRepository.findByTitle(bookTitle);

		if(user == null) {
            throw new UserNotFoundException("User not found");
		}
		if (book == null) {
			throw new BookNotFoundException("Book not found");
		}
		
		try {
			borrowingRecordRepository.save(new BorrowingRecord(user, book, borrowDate, returnDate));
		} catch (Exception e) {
		    throw new RuntimeException("Error saving borrowing record");
		}
		
		System.out.println("Added order: [" + book.getTitle() + "] [" + user.getUsername() + "]");
	}
	
	public void deleteBorrowingRecord(int id) {
		borrowingRecordRepository.deleteById(id);
	}
	
	public List<BorrowingRecord> findAllBorrowingRecords(Sort sort) {
		return borrowingRecordRepository.findAll(sort);
	}
	
	public List<BorrowingRecord> findAllBorrowingRecords() {
		return borrowingRecordRepository.findAll();
	}

	
	public List<BorrowingRecord> findBorrowingRecordsByUser(String referenceNumber) {
		return borrowingRecordRepository.findByUser_ReferenceNumber(referenceNumber);
	}

	public void updateBorrowingRecord(int recordId, boolean isReturned) {
		BorrowingRecord record = borrowingRecordRepository.findById(recordId);
		record.setReturned(isReturned);
		borrowingRecordRepository.save(record);
	}
}
