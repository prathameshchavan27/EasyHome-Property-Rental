package com.prs.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.pojos.Booking;
import com.prs.pojos.BookingStatus;
import com.prs.service.BookingPaymentReport;
import com.prs.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private BookingPaymentReport bookingPaymentReport;
    private static final String FILE = System.getProperty("user.home") + "/src/main/resources/static/reports/";
    
    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Booking>> getPendingBookings() {
        return ResponseEntity.ok(bookingService.getPendingBookings());
    }

    @PutMapping("/approve/{bookingId}")
//    @PreAuthorize("LANDLORD")
    public ResponseEntity<String> approveBooking(@PathVariable Long bookingId) {
    	System.out.println(bookingId);
        bookingService.updateBookingStatus(bookingId, BookingStatus.APPROVED);
        return ResponseEntity.ok("Booking Approved Successfully");
    }

    @PutMapping("/{bookingId}/reject")
    public ResponseEntity<String> rejectBooking(@PathVariable Long bookingId) {
        bookingService.updateBookingStatus(bookingId, BookingStatus.REJECTED);
        return ResponseEntity.ok("Booking Rejected Successfully");
    }
    
    @GetMapping("/{tenantId}")
    public ResponseEntity<?> getRequestedBookings(@PathVariable Long tenantId) {
        return ResponseEntity.ok(bookingService.getBookingsRequests(tenantId));
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
//    @GetMapping("/report")
//    public List<BookingPaymentDTO> getBookingPaymentStatement() {
//        // This method will generate the PDF, send the email, and return the booking payment data
//        return bookingPaymentReport.generateStatement();
//    }

    @GetMapping(value = "/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadBookingPaymentReport() {
        // Generate the PDF report (this writes the file to disk)
        bookingPaymentReport.generateStatement();
        
        try {
            File file = new File(FILE);
            byte[] contents = Files.readAllBytes(file.toPath());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "BookingPaymentStatement.pdf");
            headers.setContentLength(contents.length);
            
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Log error and return an error response
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
