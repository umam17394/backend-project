package com.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.binding.PlanData;
import com.project.entity.PlanCategoryEntity;
import com.project.entity.PlanMasterEntity;
import com.project.repo.PlanCategoryRepo;
import com.project.repo.PlanMasterRepo;
import com.project.service.PlanServiceImpl;

@SpringBootTest(classes = { ServiceMockitoTests.class })
public class ServiceMockitoTests {

	@Mock
	PlanMasterRepo planMasterRepo;
	@Mock
	PlanCategoryRepo pcRepo;

	@InjectMocks
	PlanServiceImpl serviceImpl;

	List<PlanMasterEntity> plans;

	@Test
	@Order(1)
	public void test_getAllPlans() {
		List<PlanMasterEntity> plans = new ArrayList<>();
		plans.add(new PlanMasterEntity(1, "medicare", LocalDate.of(2022, 9, 11), LocalDate.of(2024, 9, 11), 1, "yes"));
		plans.add(new PlanMasterEntity(2, "NJW", LocalDate.of(2020, 9, 1), LocalDate.of(2022, 5, 21), 2, "yes"));
		plans.add(new PlanMasterEntity(3, "SNAP", LocalDate.of(2019, 9, 11), LocalDate.of(2026, 6, 21), 3, "yes"));

		when(planMasterRepo.findAll()).thenReturn(plans);
		List<PlanMasterEntity> result = serviceImpl.getAllPlans();
		assertEquals(plans.size(), result.size());
	}

	@Test
	@Order(2)
	public void test_getPlanById() {
		// Arrange
		Integer planId = 1;
		PlanMasterEntity expectedPlan = new PlanMasterEntity(planId, "medicare", LocalDate.of(2022, 9, 11),
				LocalDate.of(2024, 9, 11), 1, "yes");

		when(planMasterRepo.findById(planId)).thenReturn(Optional.of(expectedPlan));

		// Act
		PlanMasterEntity actualPlan = serviceImpl.getPlanById(planId);

		// Assert
		assertEquals(expectedPlan, actualPlan);
	}

	@Test
	@Order(3)
	public void test_savePlan() {
		PlanData planData = new PlanData("medicaid", LocalDate.of(2019, 9, 11), LocalDate.of(2026, 6, 21), 4);
		when(planMasterRepo.save(any(PlanMasterEntity.class))).thenReturn(new PlanMasterEntity(1));
		boolean savePlan = serviceImpl.savePlan(planData);
		assertTrue(savePlan);
	}

	@Test
	@Order(4)
	public void test_updatePlan() {
		Integer planIdToUpdate = 1;
		PlanData updatedPlanData = new PlanData("QHPP", LocalDate.of(2023, 1, 1), LocalDate.of(2024, 12, 31), 5);

		// Mock the repository behavior
		PlanMasterEntity existingPlan = new PlanMasterEntity(planIdToUpdate, "medicare", LocalDate.of(2022, 9, 11),
				LocalDate.of(2024, 9, 11), 1, "yes");
		when(planMasterRepo.findById(planIdToUpdate)).thenReturn(Optional.of(existingPlan));
		when(planMasterRepo.save(any(PlanMasterEntity.class))).thenReturn(existingPlan); // Return the updated entity

		// Call the method under test
		boolean isUpdated = serviceImpl.updatePlan(planIdToUpdate, updatedPlanData);

		// Assert the results
		assertTrue(isUpdated);
		verify(planMasterRepo).findById(planIdToUpdate);
		verify(planMasterRepo).save(existingPlan);

		// Assert that the entity was actually updated
		PlanMasterEntity savedPlan = serviceImpl.getPlanById(planIdToUpdate); // Retrieve the saved entity
		assertEquals(updatedPlanData.getPlanName(), savedPlan.getPlanName()); // Verify plan name change
		// Add more assertions for other updated fields if needed
	}

	@Test
	@Order(5) // Assuming this test follows previous ones

	public void testDeletePlanById() {
		// Arrange
		Integer planIdToDelete = 1;
		PlanMasterEntity planToDelete = new PlanMasterEntity();
		planToDelete.setPlanId(planIdToDelete);
		// Mock repository behavior
		when(planMasterRepo.existsById(planIdToDelete)).thenReturn(true);
		doNothing().when(planMasterRepo).deleteById(planIdToDelete);
		// Act
		boolean isDeleted = serviceImpl.deletePlanById(planIdToDelete);
		// Assert
		assertTrue(isDeleted);
		verify(planMasterRepo).existsById(planIdToDelete);
		verify(planMasterRepo).deleteById(planIdToDelete);
	}

	@Test
	@Order(6)
	public void testGetPlanCategoryById() {
		// Arrange
		Integer categoryId = 1;
		PlanCategoryEntity expectedCategory = new PlanCategoryEntity(categoryId, "Test Category", "yes");

		// Mock repository behavior
		when(pcRepo.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

		// Act
		PlanCategoryEntity actualCategory = serviceImpl.getPlanCategoryById(categoryId);

		// Assert
		assertNotNull(actualCategory);
		assertEquals(expectedCategory, actualCategory);
		verify(pcRepo).findById(categoryId);
	}

	@Test
	public void testGetPlanCategoryById_NotFound() {
		// Arrange
		Integer categoryId = 1;

		// Mock repository behavior
		when(pcRepo.findById(categoryId)).thenReturn(Optional.empty());

		// Act
		PlanCategoryEntity actualCategory = serviceImpl.getPlanCategoryById(categoryId);

		// Assert
		assertNull(actualCategory);
		verify(pcRepo).findById(categoryId);
	}
	 @Test
	    public void testChangePlanStatus() {
	        // Arrange
	        Integer planId = 1;
	        String activeSw = "yes";
	        PlanCategoryEntity planCategoryEntity = new PlanCategoryEntity(planId, "Test Category", "no");

	        // Mock repository behavior
	        when(pcRepo.findById(planId)).thenReturn(Optional.of(planCategoryEntity));
	        when(pcRepo.save(planCategoryEntity)).thenReturn(planCategoryEntity);

	        // Act
	        boolean statusChanged = serviceImpl.changePlanStatus(planId, activeSw);

	        // Assert
	        assertTrue(statusChanged);
	        assertEquals(activeSw, planCategoryEntity.getActiveSw());
	        verify(pcRepo).findById(planId);
	        verify(pcRepo).save(planCategoryEntity);
	    }
	 @Test
	    public void testChangePlanStatus_NotFound() {
	        // Arrange
	        Integer planId = 1;
	        String activeSw = "yes";

	        // Mock repository behavior
	        when(pcRepo.findById(planId)).thenReturn(Optional.empty());

	        // Act
	        boolean statusChanged = serviceImpl.changePlanStatus(planId, activeSw);

	        // Assert
	        assertFalse(statusChanged);
	        verify(pcRepo).findById(planId);
	        verify(pcRepo, never()).save(any(PlanCategoryEntity.class));
	    }
}
