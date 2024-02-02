package com.project.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.project.binding.PlanData;
import com.project.entity.PlanCategoryEntity;
import com.project.entity.PlanMasterEntity;

public interface PlanService {
	
	public List<PlanCategoryEntity> getPlanCategories();
	public PlanCategoryEntity getPlanCategoryById(Integer id);
	public boolean savePlan(PlanData planData);
	public List<PlanMasterEntity>getAllPlans();
	public PlanMasterEntity getPlanById(Integer planId);
	public boolean updatePlan(Integer planId,PlanData planData);
	public boolean deletePlanById(Integer planId);
	public boolean changePlanStatus(Integer PlanId,String activeSw);
//	public ByteArrayInputStream createPdf();
	
}
