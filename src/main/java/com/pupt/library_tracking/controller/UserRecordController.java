package com.pupt.library_tracking.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pupt.library_tracking.model.BorrowingRecord;
import com.pupt.library_tracking.model.User;
import com.pupt.library_tracking.repository.UserRepository;
import com.pupt.library_tracking.service.BorrowingRecordService;

@Controller
public class UserRecordController {
	
	private BorrowingRecordService borrowingRecordService;
	private UserRepository userRepository;
	
	public UserRecordController(
			BorrowingRecordService borrowingRecordService,
			UserRepository userRepository) {
		this.borrowingRecordService = borrowingRecordService;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/user/records")
	public String borrowingRecordPage(
			Model model,
			Principal principal,
			@RequestParam(required = false) String error) {
		User user = userRepository.findByReferenceNumber(principal.getName()).get();
		
		List<BorrowingRecord> borrowingRecords = borrowingRecordService.findBorrowingRecordsByUser(user.getReferenceNumber());
		model.addAttribute("records", borrowingRecords);
		model.addAttribute("error", error);
		return "user-records";
    }
}
