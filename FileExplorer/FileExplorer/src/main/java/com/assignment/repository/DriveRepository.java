package com.assignment.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.data.repository.CrudRepository;

import com.assignment.entity.Drives;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DriveRepository extends CrudRepository<Drives, Integer> {
	List<Drives> findAllByType(String type);
	
	List<Drives> findAllByParentFolderId(Integer parentFolderId);
}
