package com.prs.controller;


import java.util.Map;

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

import com.prs.service.PaymentService;
import com.prs.service.PropertyService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    
    @Autowired
    private PropertyService propertyService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Step 1: Create Stripe Payment Intent (After Booking Approved)
    @PostMapping("/create")
    public String createPayment(@RequestParam Long bookingId, @RequestParam double amount) throws StripeException {
        return paymentService.createPaymentIntent(bookingId, amount);
    }
    

    // Step 2: Stripe Webhook for Payment Status Update
    @PostMapping("/stripe-webhook")
    public void handleStripeWebhook(@RequestBody Map<String, Object> payload) {
//        String transactionId = (String) ((Map<String, Object>) payload.get("data")).get("id");
//        paymentService.updatePaymentStatus(transactionId);
    	
        Map<String, Object> data = (Map<String, Object>) payload.get("data");
//        Map<String, Object> id = (Map<String, Object>) payload.get("pid");
        if (data != null) {
            String transactionId = (String) data.get("id");
            if (transactionId != null) {
                paymentService.updatePaymentStatus(transactionId);
            }
        }
        int p =  (int) data.get("pid");
        Long pid = (long) p;
        propertyService.updateAvailableStatus(pid);

    }
    
    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable Long bookingId){
    	return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentDetails(bookingId));
    }
    
    @GetMapping("/getpayments")
    public ResponseEntity<?> getPayments(){
    	return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayments());
    }
    
    
}
