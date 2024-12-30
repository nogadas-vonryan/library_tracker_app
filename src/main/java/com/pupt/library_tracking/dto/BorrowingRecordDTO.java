package com.pupt.library_tracking.dto;

import com.pupt.library_tracking.model.Book;
import com.pupt.library_tracking.model.BorrowingRecord;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowingRecordDTO {
	private int id;
	private boolean isReturned;
	private String borrowDate;
	private String returnDate;
	private int userId;
	private int bookId;

	public BorrowingRecordDTO(int id, 
							  boolean isReturned, 
							  String borrowDate, 
							  String returnDate, 
							  int userId,
							  int bookId) {
		this.id = id;
		this.isReturned = isReturned;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.userId = userId;
		this.bookId = bookId;
	}
	
	public BorrowingRecordDTO(BorrowingRecord borrowingRecord) {
		this.id = borrowingRecord.getId();
        this.isReturned = borrowingRecord.isReturned();
        this.borrowDate = borrowingRecord.getBorrowDate().toString();
        this.returnDate = borrowingRecord.getReturnDate().toString();
        this.userId = borrowingRecord.getUser().getId();
        this.bookId = borrowingRecord.getBook().getId();
	}
}
