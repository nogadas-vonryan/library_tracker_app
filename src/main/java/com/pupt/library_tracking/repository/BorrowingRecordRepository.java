package com.pupt.library_tracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pupt.library_tracking.model.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {
	
	@EntityGraph(value = "BorrowingRecord.userBook")
	List<BorrowingRecord> findAll();
	
    @EntityGraph(value = "BorrowingRecord.userBook")
	List<BorrowingRecord> findByUser_ReferenceNumber(String referenceNumber);
    
    @Query(value = "SELECT * FROM borrowing_record b WHERE MONTH(b.borrow_date) = :month " +
            "AND YEAR(b.borrow_date) = :year", nativeQuery = true)
    List<BorrowingRecord> findRecordsByMonthAndYear(int month, int year);
    
    @Query(value = "SELECT * FROM borrowing_record b WHERE is_returned = :isReturned", nativeQuery = true)
    List<BorrowingRecord> findRecordsIfReturned(boolean isReturned);
    
    @Query(value = "SELECT r.* "
    		+ "FROM borrowing_record r "
    		+ "JOIN user u ON r.user_id = u.id "
    		+ "JOIN book b ON r.book_id = b.id "
    		+ "WHERE u.first_name LIKE %:searchTerm% "
    		+ "   OR u.last_name LIKE %:searchTerm% "
    		+ "   OR u.reference_number LIKE %:searchTerm% "
    		+ "   OR b.title LIKE %:searchTerm% "
    		+ "   OR b.author LIKE %:searchTerm% "
    		, nativeQuery = true)
    List<BorrowingRecord> search(String searchTerm);
    
	BorrowingRecord findByBookId(int bookId);
	BorrowingRecord findById(int id);
}
