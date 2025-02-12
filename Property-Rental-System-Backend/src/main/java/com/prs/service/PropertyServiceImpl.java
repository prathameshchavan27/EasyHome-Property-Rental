package com.prs.service;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prs.dao.AmenityRepository;
import com.prs.dao.ImageRepository;
import com.prs.dao.PropertyAmenityRepository;
import com.prs.dao.PropertyRepository;
import com.prs.dao.UserDao;
import com.prs.pojos.Amenity;
import com.prs.pojos.Images;
import com.prs.pojos.Property;
import com.prs.pojos.PropertyAmenity;
import com.prs.pojos.PropertyAmenityId;
import com.prs.pojos.PropertyType;
import com.prs.pojos.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

	@Autowired
    private PropertyRepository propertyRepository;
	 
	@Autowired
    private ImageRepository imageRepository;
	
	@Autowired
	private PropertyAmenityRepository propertyAmenityRepository;
	
	@Autowired
	private AmenityRepository amenityRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private UserDao userRepository;

	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "src/main/resources/images/";

	
	@Override
	public void addProperty(String title, String description, String address, String city, double rent,String propertyType, Long ownerId,
			List<Long> amenityIds,List<MultipartFile> images) throws IOException {
		// TODO Auto-generated method stub
		List<Amenity> amenities = null;
		if (amenityIds != null && !amenityIds.isEmpty()) {
            amenities = amenityRepository.findAllById(amenityIds);
        }
		Property property = new Property(title,description, address,city,rent,propertyType,new User(ownerId),amenities);
		property.setAmenities(amenities);
//       property.getPropertyType()
        property = propertyRepository.save(property);

        // Save Images
        List<Images> imageList = new ArrayList<>();
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        for (MultipartFile file : images) {
//            
            String filePath = UPLOAD_DIR + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));


            Images image = new Images();
            image.setPropertyId(property.getId());
            image.setImageUrl(file.getOriginalFilename());
            image.setUploadedAt(LocalDateTime.now());
            imageList.add(image);
        }
        imageRepository.saveAll(imageList);

		
	}
	
	@Override
	@Transactional
	public void updateProperty(Long id, String title, String description, String address, String city, double rent, 
	                           Long ownerId, List<Long> amenityIds, List<MultipartFile> images) throws IOException {
	    // Find the existing property
	    Property property = propertyRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));

	    // Update basic property details
	    property.setTitle(title);
	    property.setDescription(description);
	    property.setAddress(address);
	    property.setCity(city);
	    property.setRent(rent);

//	    // Update owner
	    User owner = userRepository.findById(ownerId)
	            .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + ownerId));
	    property.setOwner(owner);

	    // Update amenities
	    if (amenityIds != null && !amenityIds.isEmpty()) {
	        List<Amenity> amenities = amenityRepository.findAllById(amenityIds);
	        property.setAmenities(amenities);
	    }

	    // Save updated property first
	    property = propertyRepository.save(property);

	    // Handle image updates
	    if (images != null && !images.isEmpty()) {
	        // Delete existing images related to the property
	        imageRepository.deleteByPropertyId(property.getId());

	        List<Images> imageList = new ArrayList<>();
	        File uploadDir = new File(UPLOAD_DIR);
	        if (!uploadDir.exists()) uploadDir.mkdirs();

	        for (MultipartFile file : images) {
	            String filePath = UPLOAD_DIR + File.separator + file.getOriginalFilename();
	            file.transferTo(new File(filePath));

	            Images image = new Images();
	            image.setPropertyId(property.getId());
	            image.setImageUrl(file.getOriginalFilename());
	            image.setUploadedAt(LocalDateTime.now());
	            imageList.add(image);
	        }
	        imageRepository.saveAll(imageList);
	    }
	}



	@Override
	public Optional<Property> getPropertyById(Long id) {
		// TODO Auto-generated method stub
		return propertyRepository.findById(id);
	}


	@Override
	public List<Property> getAllProperties() {
		// TODO Auto-generated method stub
		return propertyRepository.findAll();
	}


	@Override
	public List<Property> searchProperty(String city) {
		// TODO Auto-generated method stub
		return propertyRepository.findByCity(city);
	}



//	@Override
//	@Transactional 
//	public String deleteProperty(Long id) {
////	     Find the property or throw an exception if not found
//	    Property property = propertyRepository.findById(id)
//	        .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
//
////	     Delete property-amenity relationships first
//	    propertyAmenityRepository.deleteByProperty(property);
//
////	     Remove images if orphanRemoval is not enabled
//	    property.getImages().clear();
//	    property.setOwner(null);
////	    propertyRepository.flush();  
////	     Now delete the property
//	    propertyRepository.deleteById(id);
//
//	    return "Delete Successful";
//	}
	@Override
	@Transactional
	public String deleteProperty(Long id) {
	    // Fetch the property entity
	    Property property = propertyRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));

	    System.out.println("Deleting property with ID: " + id);

	    // 1️⃣ Remove the property from the owner's list
	    User owner = property.getOwner();
	    if (owner != null) {
	        owner.removeProperty(property);
	    }

	    // 2️⃣ Clear other relationships (e.g., images, amenities)
	    property.getImages().clear();
	    property.getAmenities().clear();

	    // 3️⃣ Save and flush the owner to ensure changes are persisted
	    userRepository.saveAndFlush(owner);

	    // 4️⃣ Finally, delete the property
	    propertyRepository.delete(property);
	    return "Delete Successfully";
	}

	@Override
	public List<Property> getPropertiesByLid(Long id) {
		// TODO Auto-generated method stub
		return propertyRepository.findByOwner(new User(id));
	}

	@Override
	public List<Property> getFilteredProperties(String city, Integer minRent, Integer maxRent, Boolean available, String propertyTypeStr, List<String> amenities) {
	    PropertyType propertyType = null;
	    if (propertyTypeStr != null && !propertyTypeStr.isEmpty()) {
	        try {
	            propertyType = PropertyType.valueOf(propertyTypeStr.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Invalid property type: " + propertyTypeStr);
	        }
	    }
	    return propertyRepository.findFilteredProperties(city, minRent, maxRent, available, propertyType);
	}
//	public List<Property> getFilteredProperties(String city, Integer minRent, Integer maxRent, Boolean available,
//			PropertyType propertyType, List<String> amenities) {
//		// TODO Auto-generated method stub
//		return propertyRepository.findFilteredProperties(city, minRent, maxRent, available, propertyType);
//	}

	@Override
	public void updateAvailableStatus(Long id) {
		// TODO Auto-generated method stub
		Property p = propertyRepository.findById(id).orElseThrow();
		p.setAvailable(false);
		propertyRepository.save(p);
	}
	
}
