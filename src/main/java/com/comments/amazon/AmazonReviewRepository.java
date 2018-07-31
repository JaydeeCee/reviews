package com.comments.amazon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for jpa functionalities for amazon review Entities
@Repository
public interface AmazonReviewRepository extends JpaRepository<AmazonScrapperEntity, Long>{
	
	List<AmazonScrapperEntity> findByBufferid(String bufferId);
	
}
