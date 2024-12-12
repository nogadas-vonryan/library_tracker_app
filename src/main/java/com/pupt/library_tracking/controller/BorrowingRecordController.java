package com.pupt.library_tracking.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pupt.library_tracking.exception.BookNotFoundException;
import com.pupt.library_tracking.exception.UserNotFoundException;
import com.pupt.library_tracking.model.Book;
import com.pupt.library_tracking.model.BorrowingRecord;
import com.pupt.library_tracking.model.User;
import com.pupt.library_tracking.repository.BookRepository;
import com.pupt.library_tracking.repository.BorrowingRecordRepository;
import com.pupt.library_tracking.repository.UserRepository;
import com.pupt.library_tracking.service.BorrowingRecordService;
import com.pupt.library_tracking.service.UserService;

@Controller
public class BorrowingRecordController {
	
	private BorrowingRecordService borrowingRecordService;
	
	public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
		this.borrowingRecordService = borrowingRecordService;
	}
	
	@GetMapping("/borrowing-records")
	public String borrowingRecordPage(Model model, @RequestParam(required = false) String error) {
		List<BorrowingRecord> borrowingRecords = borrowingRecordService.findAllBorrowingRecords();
		model.addAttribute("records", borrowingRecords);
		model.addAttribute("error", error);
		return "borrowing-record";
    }
	
	@PostMapping("/borrowing-records")
	public String borrowingRecordProcessing(Model model, Principal principal, @RequestParam String title) {
		try {
			borrowingRecordService.saveBorrowingRecord(principal.getName(), title);
		} catch (UserNotFoundException e) {
			return "redirect:/borrowing-records?error=user";
		} catch (BookNotFoundException e) {
			return "redirect:/borrowing-records?error=book";
		}
		return "redirect:/borrowing-records";
	}
}
