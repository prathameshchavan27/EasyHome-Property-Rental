package com.prs.pojos;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long bookingId;
	private double amount;
	private LocalDate paymentDate;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	private String transactionId;  // Stripe PaymentIntent ID

	private String currency = "INR";  // Default currency
	private String paymentMethod;  // Card, UPI, etc.
}

