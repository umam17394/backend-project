package com.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.PlanCategoryEntity;

public interface PlanCategoryRepo extends JpaRepository<PlanCategoryEntity, Integer>{

}
