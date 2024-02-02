package com.project.binding;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanData {
	private String planName;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer categoryId;

}
