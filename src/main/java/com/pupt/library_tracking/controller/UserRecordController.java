package com.pupt.library_tracking.controller;

import java.security.Principal;
import java.time.LocalDate;
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
			@RequestParam(required = false) String sortOrder,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String month,
			@RequestParam(required = false) String year,
			@RequestParam(required = false) String error) {
		
		User user = userRepository.findByReferenceNumber(principal.getName()).get();
		List<BorrowingRecord> records = borrowingRecordService.findBorrowingRecordsByUser(user.getReferenceNumber());
		
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
		return "user-records";
    }
}
