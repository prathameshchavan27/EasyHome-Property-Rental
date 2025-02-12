package com.prs.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prs.dto.FeedbackDTO;
import com.prs.pojos.Feedback;
import com.prs.service.FeedbackService;


@RestController
//@RequestMapping("/feedback")
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;
	
	@PostMapping("/property/{propertyId}/feedback")
	public ResponseEntity<?> addFeedback(
            @PathVariable Long propertyId,
            @RequestBody FeedbackDTO feedback) {
        try {
            // Call the service method to add feedback
            feedbackService.addFeedback(propertyId,feedback.getUserId(), feedback.getComments());
            return ResponseEntity.status(HttpStatus.CREATED).body("Feedback added successfully!");
        } catch (Exception e) {
            // Handle exceptions and return appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add feedback");
        }
    }
	
	 // GET /feedback/property/{propertyId} - Get feedback for a property
    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<?> getFeedbackByProperty(@PathVariable Long propertyId) {
        try {
            List<Feedback> feedbackList = feedbackService.getFeedbackByProperty(propertyId);
            return ResponseEntity.ok(feedbackList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // GET /feedback/user/{userId} - Get feedback given by a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable Long userId) {
        try {
            List<Feedback> feedbackList = feedbackService.getFeedbackByUser(userId);
            return ResponseEntity.ok(feedbackList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
	
}
