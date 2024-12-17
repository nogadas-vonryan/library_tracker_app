package com.pupt.library_tracking.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pupt.library_tracking.model.BorrowingRecord;
import com.pupt.library_tracking.service.BorrowingRecordService;

@Controller
public class UserRecordController {
	
	private BorrowingRecordService borrowingRecordService;
	
	public UserRecordController(BorrowingRecordService borrowingRecordService) {
		this.borrowingRecordService = borrowingRecordService;
	}
	
	@GetMapping("/user/records")
	public String borrowingRecordPage(Model model, @RequestParam(required = false) String error) {
//		TODO: Make this data accessible only to the user who is currently logged in
		List<BorrowingRecord> borrowingRecords = borrowingRecordService.findAllBorrowingRecords();
		model.addAttribute("records", borrowingRecords);
		model.addAttribute("error", error);
		return "borrowing-record";
    }
}
