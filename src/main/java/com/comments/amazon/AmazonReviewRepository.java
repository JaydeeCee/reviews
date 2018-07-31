package com.comments.amazon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AmazonReviewRepository extends JpaRepository<AmazonScrapperEntity, Long>{
	
	List<AmazonScrapperEntity> findByBufferid(String bufferId);
	
}
