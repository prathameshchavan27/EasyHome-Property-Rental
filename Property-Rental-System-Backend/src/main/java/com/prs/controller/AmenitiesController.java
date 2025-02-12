package com.prs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.pojos.Amenity;
import com.prs.service.AmenityService;


@RestController
public class AmenitiesController {
	
	@Autowired
	private AmenityService amenityService;

	@PostMapping("/landlord/addAmenities")
	public ResponseEntity<?> addAmenities(@RequestBody Amenity amenity){
		return ResponseEntity.status(HttpStatus.CREATED).body(amenityService.addAmenity(amenity));
	}
	
	@GetMapping("/landlord/all")
	public ResponseEntity<?> getAllAmenities(){
		return ResponseEntity.status(HttpStatus.CREATED).body(amenityService.getAllAmenities());
	}
	
	@PostMapping("/landlord/{propertyId}/amenities")
	public ResponseEntity<?> addAmenityToProperty(@RequestBody Amenity amenity, @PathVariable Long propertyId){
		return ResponseEntity.status(HttpStatus.CREATED).body(amenityService.addAmenityToProperty(propertyId,amenity));
	}
	
	
 }
