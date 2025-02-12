package com.prs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prs.dto.BookingPaymentDTO;
import com.prs.pojos.Booking;
import com.prs.pojos.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByTenantId(Long tenantId);
    
    @Query("SELECT new com.prs.dto.BookingPaymentDTO(p.title, l.name, t.name, b.id,b.status, pm.amount, pm.transactionId) " +
            "FROM Booking b " +
            "JOIN Property p ON b.propertyId = p.id " +
            "JOIN User l ON p.owner.id = l.id " +
            "JOIN User t ON b.tenantId = t.id " +
            "JOIN Payment pm ON pm.bookingId = b.id")
     List<BookingPaymentDTO> getBookingPayments();
}
