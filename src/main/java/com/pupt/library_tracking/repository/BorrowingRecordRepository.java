package com.pupt.library_tracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pupt.library_tracking.model.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {
	
	@EntityGraph(value = "BorrowingRecord.userBook")
	List<BorrowingRecord> findAll();
	BorrowingRecord findByUserId(int userId);
	BorrowingRecord findByBookId(int bookId);
	BorrowingRecord findById(int id);
}
