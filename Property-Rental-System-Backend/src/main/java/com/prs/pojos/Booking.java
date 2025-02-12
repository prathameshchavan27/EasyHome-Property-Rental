package com.prs.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity 
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long propertyId;
    private Long tenantId;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Booking(Long propertyId, Long tenantId, LocalDate startDate, LocalDate endDate, BookingStatus status) {
		super();
		this.propertyId = propertyId;
		this.tenantId = tenantId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

    // Getters and Setters
    
}
