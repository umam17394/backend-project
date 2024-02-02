package com.project.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PLAN_MASTER")
public class PlanMasterEntity {

	public PlanMasterEntity(int i) {
		this.planId = i;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PLAN_ID")
	private Integer planId;

	@Column(name = "PLAN_NAME")
	private String planName;

	@Column(name = "PLAN_START_DATE")
	private LocalDate startDate;

	@Column(name = "PLAN_END_DATE")
	private LocalDate endDate;

	@Column(name = "CATEGORY_ID")
	private Integer categoryId;
	
	@Column(name = "ACTIVE_SW")
	private String activeSw;
	
}
