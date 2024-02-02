package com.project.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.binding.PlanData;
import com.project.entity.PlanCategoryEntity;
import com.project.entity.PlanMasterEntity;
import com.project.service.PlanService;

@RestController
@RequestMapping("/plans")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlanController {

	@Autowired
	private PlanService planService;

	private Map<String, String> msgs = new HashMap<>();

	@GetMapping("/categories")
	public ResponseEntity<List<PlanCategoryEntity>> getPlanCategories() {
		List<PlanCategoryEntity> planCategories = planService.getPlanCategories();
		return new ResponseEntity<>(planCategories, HttpStatus.OK);
	}

	@PostMapping("/savePlan")
	public ResponseEntity<String> savePlan(@RequestBody PlanData planData) {
		boolean saved = planService.savePlan(planData);
		if (saved) {
			return new ResponseEntity<>(msgs.get("planSaveSucc"), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(msgs.get("planSaveFail"), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<PlanMasterEntity>> getAllPlans() {
		List<PlanMasterEntity> allPlans = planService.getAllPlans();
		return new ResponseEntity<>(allPlans, HttpStatus.OK);
	}

	@GetMapping("/{planId}")
	public ResponseEntity<PlanMasterEntity> getPlanById(@PathVariable Integer planId) {
		PlanMasterEntity plan = planService.getPlanById(planId);
		if (plan != null) {
			return new ResponseEntity<>(plan, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update/{planId}")
	public ResponseEntity<String> updatePlan(@PathVariable Integer planId, @RequestBody PlanData planData) {
		boolean updated = planService.updatePlan(planId, planData);
		if (updated) {
			return new ResponseEntity<>(msgs.get("PlanUpdateSucc"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(msgs.get("PlanUpdateFail"), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId) {
		boolean deleted = planService.deletePlanById(planId);
		if (deleted) {
			return new ResponseEntity<>(msgs.get("PlanDeleteSucc"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(msgs.get("PlanDeleteFail"), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/change-status/{planId}")
	public ResponseEntity<String> changePlanStatus(@PathVariable Integer planId, @RequestParam String activeSw) {
		boolean statusChanged = planService.changePlanStatus(planId, activeSw);
		if (statusChanged) {
			return new ResponseEntity<>(msgs.get("PlanStatusChangeSuccc"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(msgs.get("PlanStatusChageFail"), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<PlanCategoryEntity> getGroup(@PathVariable Integer id) {
		PlanCategoryEntity planCategory = planService.getPlanCategoryById(id);

		if (planCategory != null) {
			return new ResponseEntity<>(planCategory, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	/*
	 * @GetMapping("/pdf") public ResponseEntity<InputStreamResource> createPdf() {
	 * ByteArrayInputStream pdf = planService.createPdf(); HttpHeaders httpHeaders =
	 * new HttpHeaders(); httpHeaders.add("Content-Disposition",
	 * "inline;filename=lcwd.pdf");
	 * 
	 * return ResponseEntity .ok() .headers(httpHeaders)
	 * .contentType(MediaType.APPLICATION_PDF) .body(new InputStreamResource(pdf));
	 * }
	 */

}
