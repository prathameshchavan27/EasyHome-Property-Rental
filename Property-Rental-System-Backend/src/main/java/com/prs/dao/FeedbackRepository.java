package com.prs.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.pojos.Feedback;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	List<Feedback> findByPropertyId(Long propertyId);

	List<Feedback> findByOwnerId(Long ownerId);

	
}
