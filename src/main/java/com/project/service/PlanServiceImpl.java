package com.project.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.binding.PlanData;
import com.project.entity.PlanCategoryEntity;
import com.project.entity.PlanMasterEntity;
import com.project.repo.PlanCategoryRepo;
import com.project.repo.PlanMasterRepo;
import com.project.utils.PdfUtils;

@Service
public class PlanServiceImpl implements PlanService {
	@Autowired
	private PlanCategoryRepo pcRepo;
	@Autowired
	private PlanMasterRepo pmRepo;

	@Override
	public List<PlanCategoryEntity> getPlanCategories() {
		return pcRepo.findAll();
	}

	@Override
	public boolean savePlan(PlanData planData) {
		PlanMasterEntity pce = new PlanMasterEntity();
		BeanUtils.copyProperties(planData, pce);
		PlanMasterEntity save = pmRepo.save(pce);
		return save.getPlanId() != null;
	}

	@Override
	public List<PlanMasterEntity> getAllPlans() {
		List<PlanMasterEntity> allPlans = pmRepo.findAll();
		return allPlans;
	}

	@Override
	public PlanMasterEntity getPlanById(Integer planId) {
		Optional<PlanMasterEntity> optionalPlan = pmRepo.findById(planId);
		return optionalPlan.orElse(null);
	}

	@Override
	public boolean updatePlan(Integer planId, PlanData planData) {
		Optional<PlanMasterEntity> optionalPlan = pmRepo.findById(planId);
		if (optionalPlan.isPresent()) {
			PlanMasterEntity planMasterEntity = optionalPlan.get();
			BeanUtils.copyProperties(planData, planMasterEntity);
			pmRepo.save(planMasterEntity);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deletePlanById(Integer planId) {
		if (planId != null && pmRepo.existsById(planId)) {
			pmRepo.deleteById(planId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean changePlanStatus(Integer planId, String activeSw) {
		Optional<PlanCategoryEntity> optionalPlan = pcRepo.findById(planId);
		if (optionalPlan.isPresent()) {
			PlanCategoryEntity planCategoryEntity = optionalPlan.get();
			planCategoryEntity.setActiveSw(activeSw);
			pcRepo.save(planCategoryEntity);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public PlanCategoryEntity getPlanCategoryById(Integer id) {
		Optional<PlanCategoryEntity> pcEntity = pcRepo.findById(id);
		return pcEntity.orElse(null);
	}

	/*
	 * @Override public ByteArrayInputStream createPdf() { List<PlanMasterEntity>
	 * plans = pmRepo.findAll(); List<PlanCategoryEntity> categories =
	 * pcRepo.findAll(); ByteArrayInputStream createPdf = PdfUtils.createPdf(plans,
	 * categories); return createPdf; }
	 */

}
