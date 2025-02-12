package com.prs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.dao.BookingRepository;
import com.prs.dao.UserDao;
import com.prs.dto.BookingPaymentDTO;
import com.prs.pojos.Booking;
import com.prs.pojos.BookingStatus;
import com.prs.pojos.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserDao userRepository;
    

    @Autowired
    private EmailService emailService;
    
    @Override
    public Booking createBooking(Booking booking) {
    	Booking b = new Booking(booking.getPropertyId(), booking.getTenantId(), booking.getStartDate(), booking.getEndDate(), booking.getStatus());
        return bookingRepository.save(b);
    }

    @Override
    public List<Booking> getPendingBookings() {
        return bookingRepository.findByStatus(BookingStatus.PENDING);
    }

    @Override
    public List<Booking> getBookingsRequests(Long id){
    	return bookingRepository.findByTenantId(id);
    }
    
    @Override
    public void updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(status);
        bookingRepository.save(booking);
//        User user = userRepository.findById(booking.getTenantId()).orElseThrow();
//        user.getEmail()
        // Notify tenant
//        String message = "Your booking has been " + status.toString().toLowerCase();
//        emailService.sendBookingConfirmation(user.getEmail(), message);
    }

	@Override
	public List<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return bookingRepository.findAll();
	}
	
	@Override
	public List<BookingPaymentDTO> fetchBookingPayments() {
        return bookingRepository.getBookingPayments();
    }
	
//	@Override
//	public List<Booking> getUserBookings(Long userId) {
//		// TODO Auto-generated method stub
//		return bookingRepository.findByTenantId(userId);
//	}
}
