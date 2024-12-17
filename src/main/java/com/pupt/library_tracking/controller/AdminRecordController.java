package com.pupt.library_tracking.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pupt.library_tracking.exception.BookNotFoundException;
import com.pupt.library_tracking.exception.UserNotFoundException;
import com.pupt.library_tracking.model.BorrowingRecord;
import com.pupt.library_tracking.service.BorrowingRecordService;

@Controller
public class AdminRecordController {
	
	private BorrowingRecordService borrowingRecordService;

	public AdminRecordController(BorrowingRecordService borrowingRecordService) {
		this.borrowingRecordService = borrowingRecordService;
	}
	
	@GetMapping("/admin/records")
	public String borrowingRecordPage(Model model, @RequestParam(required = false) String error) {
		List<BorrowingRecord> borrowingRecords = borrowingRecordService.findAllBorrowingRecords();
		model.addAttribute("records", borrowingRecords);
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
	
	@PostMapping("/admin/records/delete")
	public String borrowingRecordDeleteProcessing(@RequestParam int recordId) {
		borrowingRecordService.deleteBorrowingRecord(recordId);
		return "redirect:/admin/records";
	}
}
