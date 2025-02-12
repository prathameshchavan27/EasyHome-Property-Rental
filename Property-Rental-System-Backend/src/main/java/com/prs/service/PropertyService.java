package com.prs.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.prs.pojos.Amenity;
import com.prs.pojos.Property;
import com.prs.pojos.PropertyType;


public interface PropertyService {
//	void addProperty( String title,String description,String address,String city,double rent,Long ownerId,List<MultipartFile> images) throws IOException;

	Optional<Property> getPropertyById(Long id);

	List<Property> getAllProperties();

	List<Property> searchProperty(String city);

	String deleteProperty(Long id);

	List<Property> getPropertiesByLid(Long id);

	void updateProperty(Long id, String title, String description, String address, String city, double rent,
			Long ownerId, List<Long> amenityIds, List<MultipartFile> images) throws IOException;

	void addProperty(String title, String description, String address, String city, double rent, String type,
			Long ownerId, List<Long> amenityIds, List<MultipartFile> images) throws IOException;

	List<Property> getFilteredProperties(String city, Integer minRent, Integer maxRent, Boolean available,
			String propertyType, List<String> amenities);

	void updateAvailableStatus(Long id);
}
