package com.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.PlanMasterEntity;

public interface PlanMasterRepo extends JpaRepository<PlanMasterEntity, Integer>{

}
