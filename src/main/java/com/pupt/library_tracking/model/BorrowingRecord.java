package com.pupt.library_tracking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NamedEntityGraph(name = "BorrowingRecord.userBook", attributeNodes = {
		@NamedAttributeNode("user"),
		@NamedAttributeNode("book")
})
public class BorrowingRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private boolean isReturned;
	private String borrowDate;
	private String returnDate;
	
	protected BorrowingRecord() { }
	
	public BorrowingRecord(User user, Book book, String borrowDate, String returnDate) {
		this.user = user;
		this.book = book;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.isReturned = false;
	}
	
	@ManyToOne
	private Book book;
	
	@ManyToOne
	private User user;
}
