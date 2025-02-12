package com.prs.service;

import java.util.List;

import com.prs.pojos.Feedback;

public interface FeedbackService {

	void addFeedback(Long propertyId, Long tenantId, String comments) throws Exception;

	List<Feedback> getFeedbackByProperty(Long propertyId);

	List<Feedback> getFeedbackByUser(Long userId);

}
