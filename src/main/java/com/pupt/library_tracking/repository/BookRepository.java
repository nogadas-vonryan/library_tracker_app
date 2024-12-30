package com.pupt.library_tracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pupt.library_tracking.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	Book findByTitle(String title);
	Book findByAuthor(String author);
	Book findById(int id);	
	
	@Query(value = "SELECT * "
    		+ "FROM book "
    		+ "WHERE title LIKE %:searchTerm% "
    		+ "   OR author LIKE %:searchTerm% "
    		, nativeQuery = true)
    List<Book> search(String searchTerm);
}
