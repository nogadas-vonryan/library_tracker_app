package com.pupt.library_tracking.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pupt.library_tracking.exception.BookNotFoundException;
import com.pupt.library_tracking.exception.UserNotFoundException;
import com.pupt.library_tracking.model.BorrowingRecord;
import com.pupt.library_tracking.repository.BookRepository;
import com.pupt.library_tracking.repository.BorrowingRecordRepository;
import com.pupt.library_tracking.service.BorrowingRecordService;

@Controller
public class AdminRecordController {
	
	private BorrowingRecordService borrowingRecordService;
	private BorrowingRecordRepository borrowingRecordRepository;

	public AdminRecordController(BorrowingRecordService borrowingRecordService,
			BorrowingRecordRepository borrowingRecordRepository) {
		this.borrowingRecordService = borrowingRecordService;
		this.borrowingRecordRepository = borrowingRecordRepository;
	}
	
	@GetMapping("/admin/records")
	public String borrowingRecordPage(Model model, 
			@RequestParam(required = false) String search,
			@RequestParam(required = false) String sortBy,
			@RequestParam(required = false) String sortOrder,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String month,
			@RequestParam(required = false) String year,
			@RequestParam(required = false) String error) {
		
		List<BorrowingRecord> records;
		
		if(search == null) search = "";
		
		records = borrowingRecordRepository.search(search);
		records = records.stream()
			.filter(record -> status == null || (status.equals("returned") && record.isReturned()) || (status.equals("borrowing") && !record.isReturned()))
			.filter(record -> month == null || LocalDate.parse(record.getBorrowDate()).getMonthValue() == Integer.parseInt(month))
			.filter(record -> year == null || LocalDate.parse(record.getBorrowDate()).getYear() == Integer.parseInt(year))
			.sorted((record1, record2) -> {
		        LocalDate date1 = LocalDate.parse(record1.getBorrowDate());
		        LocalDate date2 = LocalDate.parse(record2.getBorrowDate());
		        
		        if (sortOrder != null && sortOrder.equals("asc")) {
		            return date1.compareTo(date2);
		        } else {
		            return date2.compareTo(date1);
		        }
		    })
			.toList();
	
		model.addAttribute("records", records);
		model.addAttribute("error", error);
		return "admin-records";
    }
	
	@GetMapping("/admin/records/add")
	public String borrowingRecordAddPage(Model model) {
		return "admin-records-add";
	}
	
	@PostMapping("/admin/records/add")
	public String borrowingRecordAddProcessing(
			@RequestParam String studentNumber,
			@RequestParam String bookTitle,
			@RequestParam String returnDate) {
		
		try {	
			borrowingRecordService.saveBorrowingRecord(studentNumber, bookTitle, LocalDate.now().toString(), returnDate);
		} catch (UserNotFoundException e) {
			return "redirect:/admin/records?error=user";
		} catch (BookNotFoundException e) {
			return "redirect:/admin/records?error=book";
		}
		return "redirect:/admin/records";
	}
	
	@PostMapping("/admin/records")
	public String borrowingRecordPage(
			@RequestParam int recordId, 
			@RequestParam boolean isReturned, 
			@RequestParam(required = false) String error) {
		borrowingRecordService.updateBorrowingRecord(recordId, isReturned);
		return "redirect:/admin/records";
	}
	
	@PostMapping("/admin/records/delete")
	public String borrowingRecordDeleteProcessing(@RequestParam int recordId) {
		borrowingRecordService.deleteBorrowingRecord(recordId);
		return "redirect:/admin/records";
	}
}
