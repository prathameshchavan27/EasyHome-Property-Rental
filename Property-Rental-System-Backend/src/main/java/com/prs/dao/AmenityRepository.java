package com.prs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.pojos.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

}
