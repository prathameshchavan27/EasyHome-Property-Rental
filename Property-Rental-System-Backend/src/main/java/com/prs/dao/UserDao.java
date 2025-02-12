package com.prs.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prs.dto.BookingPaymentDTO;
import com.prs.pojos.User;

public interface UserDao extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String username);
	

	
}
