package com.prs.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.prs.pojos.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByTransactionId(String transactionId);

	Payment findByBookingId(Long bookingId);
}
