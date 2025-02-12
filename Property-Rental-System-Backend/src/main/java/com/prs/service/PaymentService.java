package com.prs.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.prs.dao.PaymentRepository;
import com.prs.pojos.Payment;
import com.prs.pojos.PaymentStatus;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService {
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final com.prs.dao.PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Create Stripe Payment Intent
    public String createPaymentIntent(Long bookingId, double amount) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (amount * 100))  // Convert to 
                .setCurrency("inr")
                .putAllMetadata(Map.of("bookingId", bookingId.toString()))
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        // Save payment in DB
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionId(intent.getId()); // Store Stripe PaymentIntent ID
        paymentRepository.save(payment);

        return intent.getClientSecret();
    }

    // Update Payment on Successful Payment
    public void updatePaymentStatus(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId);
        if (payment != null) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            payment.setPaymentDate(LocalDate.now());
            paymentRepository.save(payment);
        }
    }

	public Payment getPaymentDetails(Long bookingId) {
		// TODO Auto-generated method stub
		return paymentRepository.findByBookingId(bookingId);
	}


	public List<Payment> getPayments() {
		// TODO Auto-generated method stub
		
		return paymentRepository.findAll();
	}
}
