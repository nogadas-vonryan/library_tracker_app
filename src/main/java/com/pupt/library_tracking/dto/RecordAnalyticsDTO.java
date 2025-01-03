package com.pupt.library_tracking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecordAnalyticsDTO {
	 private String month;
	 private long count;
	 
	public RecordAnalyticsDTO(String month, long count) {
		this.month = month;
		this.count = count;
	}
}
