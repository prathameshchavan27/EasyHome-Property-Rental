package com.prs.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.dao.FeedbackRepository;
import com.prs.pojos.Feedback;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
	
	 @Autowired
	    private FeedbackRepository feedbackRepository;
	
	 @Override
	    public void addFeedback(Long propertyId, Long tenantId, String comments) throws Exception {
		 Feedback feedback = new Feedback(propertyId, tenantId,comments);

	        feedbackRepository.save(feedback);
	    }

	 @Override
	    public List<Feedback> getFeedbackByProperty(Long propertyId) {
	        return feedbackRepository.findByPropertyId(propertyId);
	    }

	    @Override
	    public List<Feedback> getFeedbackByUser(Long userId) {
	        return feedbackRepository.findByOwnerId(userId);
	    }
	



}
