package com.prs.dto;

import com.prs.pojos.BookingStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class BookingPaymentDTO {
    private String title;
    private String owner;
    private String tenant;
    private Long bookingId;
    private Double amount;
    private String transactionId;
    private BookingStatus status;
    
	public BookingPaymentDTO(String title, String owner, String tenant, Long bookingId,BookingStatus status, Double amount,
			String transactionId) {
		super();
		this.title = title;
		this.owner = owner;
		this.tenant = tenant;
		this.bookingId = bookingId;
		this.amount = amount;
		this.transactionId = transactionId;
		this.status = status;
	}

   

    // Getters and Setters
}
