package com.project.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanResponceData {
	private Integer planId;
	private String planName;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer categoryId;
	private Integer categoryName;
}
