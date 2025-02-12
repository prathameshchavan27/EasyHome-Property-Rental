package com.prs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.dao.BookingRepository;
import com.prs.dao.PaymentRepository;
import com.prs.pojos.Booking;
import com.prs.pojos.BookingStatus;
import com.prs.pojos.Payment;
import com.prs.pojos.PaymentStatus;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookController {

	@Autowired 
	private PaymentRepository paymentRepository;
	

	@Autowired 
	private BookingRepository bookingRepository;
	
    private static final String WEBHOOK_SECRET = "whsec_856bf3873cb4960727d5eaf5d6055593cc80460c585f06ea831305ab65554a13"; // From Stripe Dashboard

//    @PostMapping("/stripe-webhook")
//    public void handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
//        try {
//            Event event = Webhook.constructEvent(payload, sigHeader, WEBHOOK_SECRET);
//
//            if ("payment_intent.succeeded".equals(event.getType())) {
//                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
//                String transactionId = paymentIntent.getId();
//                System.out.println(transactionId);
//                // Update booking status in DB
//                Payment p = paymentRepository.findByTransactionId(transactionId);
//                System.out.println(p);
//                p.setPaymentStatus(PaymentStatus.COMPLETED);
//                paymentRepository.save(p);
////                Booking b = bookingRepository.findById(p.getBookingId()).orElseThrow();
////                b.setStatus(BookingStatus.)
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Webhook error: " + e.getMessage());
//        }
//    }
    
    @PostMapping("/stripe-webhook")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload, 
            @RequestHeader("Stripe-Signature") String sigHeader) {
        
        try {
            // Log received payload
            System.out.println("Received Stripe Webhook: " + payload);
            
            Event event = Webhook.constructEvent(payload, sigHeader, WEBHOOK_SECRET);

            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
                String transactionId = paymentIntent.getId();
                
                System.out.println("Payment successful for Transaction ID: " + transactionId);
                
                // Fetch the payment record from DB
                Payment p = paymentRepository.findByTransactionId(transactionId);
                
                if (p == null) {
                    System.out.println("No payment record found for transaction ID: " + transactionId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment record not found.");
                }

                // Update payment status
                p.setPaymentStatus(PaymentStatus.COMPLETED);
                paymentRepository.save(p);
                
                System.out.println("Updated payment status for Transaction ID: " + transactionId);

                // Fetch associated booking
                Booking b = bookingRepository.findById(p.getBookingId()).orElse(null);
                if (b == null) {
                    System.out.println("No booking record found for Booking ID: " + p.getBookingId());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking record not found.");
                }

                // Update booking status
                b.setStatus(BookingStatus.APPROVED);
                bookingRepository.save(b);
                
                System.out.println("Updated booking status for Booking ID: " + p.getBookingId());

                return ResponseEntity.ok("Webhook processed successfully");
            }
            
        } catch (Exception e) {
            System.err.println("Webhook error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error: " + e.getMessage());
        }
        
        return ResponseEntity.ok("Event ignored");
    }

    
   

}
