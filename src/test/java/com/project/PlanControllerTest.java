package com.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.binding.PlanData;
import com.project.controller.PlanController;
import com.project.entity.PlanCategoryEntity;
import com.project.entity.PlanMasterEntity;
import com.project.service.PlanService;

class PlanControllerTest {

	@Mock
	private PlanService planService;

	@InjectMocks
	private PlanController planController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetPlanCategories() {
		List<PlanCategoryEntity> categories = new ArrayList<>();
		categories.add(new PlanCategoryEntity(1, "Category 1", "yes"));
		categories.add(new PlanCategoryEntity(2, "Category 2", "no"));

		when(planService.getPlanCategories()).thenReturn(categories);

		ResponseEntity<List<PlanCategoryEntity>> responseEntity = planController.getPlanCategories();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(2, responseEntity.getBody().size());
	}

	@Test
	void testSavePlan_Success() {
		PlanData planData = new PlanData("Test Plan", null, null, 1);
		when(planService.savePlan(any(PlanData.class))).thenReturn(true);

		ResponseEntity<String> responseEntity = planController.savePlan(planData);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	void testSavePlan_Failure() {
		PlanData planData = new PlanData("Test Plan", null, null, 1);
		when(planService.savePlan(any(PlanData.class))).thenReturn(false);

		ResponseEntity<String> responseEntity = planController.savePlan(planData);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	void testGetAllPlans() {
		List<PlanMasterEntity> plans = new ArrayList<>();
		plans.add(new PlanMasterEntity(1, "Plan 1", null, null, 1, "yes"));
		plans.add(new PlanMasterEntity(2, "Plan 2", null, null, 2, "yes"));

		when(planService.getAllPlans()).thenReturn(plans);

		ResponseEntity<List<PlanMasterEntity>> responseEntity = planController.getAllPlans();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(2, responseEntity.getBody().size());
	}

	@Test
	void testGetPlanById_Success() {
		Integer planId = 1;
		PlanMasterEntity plan = new PlanMasterEntity(planId, "Test Plan", null, null, 1, "yes");

		when(planService.getPlanById(planId)).thenReturn(plan);

		ResponseEntity<PlanMasterEntity> responseEntity = planController.getPlanById(planId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(planId, responseEntity.getBody().getPlanId());
	}

	@Test
	void testGetPlanById_NotFound() {
		Integer planId = 1;

		when(planService.getPlanById(planId)).thenReturn(null);

		ResponseEntity<PlanMasterEntity> responseEntity = planController.getPlanById(planId);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	void testUpdatePlan_Success() {
		Integer planId = 1;
		PlanData planData = new PlanData("Test Plan", null, null, 1);

		when(planService.updatePlan(planId, planData)).thenReturn(true);

		ResponseEntity<String> responseEntity = planController.updatePlan(planId, planData);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testUpdatePlan_NotFound() {
		Integer planId = 1;
		PlanData planData = new PlanData("Test Plan", null, null, 1);

		when(planService.updatePlan(planId, planData)).thenReturn(false);

		ResponseEntity<String> responseEntity = planController.updatePlan(planId, planData);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	void testDeletePlan_Success() {
		Integer planId = 1;

		when(planService.deletePlanById(planId)).thenReturn(true);

		ResponseEntity<String> responseEntity = planController.deletePlan(planId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testDeletePlan_NotFound() {
		Integer planId = 1;

		when(planService.deletePlanById(planId)).thenReturn(false);

		ResponseEntity<String> responseEntity = planController.deletePlan(planId);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	void testChangePlanStatus_Success() {
		Integer planId = 1;
		String activeSw = "yes";

		when(planService.changePlanStatus(planId, activeSw)).thenReturn(true);

		ResponseEntity<String> responseEntity = planController.changePlanStatus(planId, activeSw);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testChangePlanStatus_NotFound() {
		Integer planId = 1;
		String activeSw = "yes";

		when(planService.changePlanStatus(planId, activeSw)).thenReturn(false);

		ResponseEntity<String> responseEntity = planController.changePlanStatus(planId, activeSw);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	void testGetPlanCategoryById_Success() {
		Integer categoryId = 1;
		PlanCategoryEntity category = new PlanCategoryEntity(categoryId, "Category 1", "yes");

		when(planService.getPlanCategoryById(categoryId)).thenReturn(category);

		ResponseEntity<PlanCategoryEntity> responseEntity = planController.getGroup(categoryId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(categoryId, responseEntity.getBody().getCategoryId());
	}

	@Test
	void testGetPlanCategoryById_NotFound() {
		Integer categoryId = 1;

		when(planService.getPlanCategoryById(categoryId)).thenReturn(null);

		ResponseEntity<PlanCategoryEntity> responseEntity = planController.getGroup(categoryId);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
