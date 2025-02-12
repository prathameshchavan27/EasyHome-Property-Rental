package com.prs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.dao.AmenityRepository;
import com.prs.dao.PropertyRepository;
import com.prs.pojos.Amenity;
import com.prs.pojos.Property;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AmenityServiceImpl implements AmenityService {
	@Autowired
	private AmenityRepository amenityRepository;
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Override
	public String addAmenity(Amenity amenity) {
		// TODO Auto-generated method stub
		amenityRepository.save(amenity);
		return "Amenity added successfully";
	}


	@Override
	public List<Amenity> getAllAmenities() {
		// TODO Auto-generated method stub
		return amenityRepository.findAll();
	}


	@Override
	public String addAmenityToProperty(Long propertyId, Amenity amenity) {
		// TODO Auto-generated method stub
		List<Property> pr = null;
		List<Amenity> ams = new ArrayList<>();
		if(amenity.getProperties()==null) {
			pr = new ArrayList<>();
			pr.add(new Property(propertyId));
			amenity.setProperties(pr);
		}else {
			amenity.getProperties().add(new Property(propertyId));
			amenity.setProperties(amenity.getProperties());
		}
		amenityRepository.save(amenity);
		Property p = propertyRepository.findById(propertyId).orElseThrow();
		ams = p.getAmenities();
		ams.add(amenity);
		
		p.setAmenities(ams);
		return "Amenity added successfully";
	}

}
