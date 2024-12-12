package com.pupt.library_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pupt.library_tracking.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	Book findByTitle(String title);
	Book findByAuthor(String author);
	Book findById(int id);	
}
