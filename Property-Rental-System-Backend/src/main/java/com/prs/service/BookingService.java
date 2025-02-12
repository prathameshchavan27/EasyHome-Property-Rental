package com.prs.service;

import java.util.List;

import com.prs.dto.BookingPaymentDTO;
import com.prs.pojos.Booking;
import com.prs.pojos.BookingStatus;

public interface BookingService {

	List<Booking> getPendingBookings();

	void updateBookingStatus(Long bookingId, BookingStatus status);

	Booking createBooking(Booking booking);

	List<Booking> getBookingsRequests(Long id);

	List<Booking> getAllBookings();

	List<BookingPaymentDTO> fetchBookingPayments();

//	List<Booking> getUserBookings(Long userId);

}
