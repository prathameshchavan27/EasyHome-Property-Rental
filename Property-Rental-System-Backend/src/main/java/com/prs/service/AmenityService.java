package com.prs.service;

import java.util.List;

import com.prs.pojos.Amenity;

public interface AmenityService {

	String addAmenity(Amenity amenity);

	List<Amenity> getAllAmenities();

	String addAmenityToProperty(Long propertyId, Amenity amenity);

}
