package com.pupt.library_tracking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.ToString;

@Getter
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
	private String borrowDate;
	private String returnDate;
	
	protected BorrowingRecord() { }
	
	public BorrowingRecord(User user, Book book) {
		this.user = user;
		this.book = book;
//		this.borrowDate = borrowDate;
//		this.returnDate = returnDate;
	}
	
	@ManyToOne
	private Book book;
	
	@ManyToOne
	private User user;
}
