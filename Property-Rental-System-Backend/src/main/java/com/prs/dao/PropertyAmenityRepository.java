package com.prs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prs.pojos.Property;
import com.prs.pojos.PropertyAmenity;
import com.prs.pojos.PropertyAmenityId;

import jakarta.transaction.Transactional;

public interface PropertyAmenityRepository extends JpaRepository<PropertyAmenity, PropertyAmenityId> {

	 List<PropertyAmenity> findByProperty(Property property);
	 void deleteByProperty(Property property);
}
