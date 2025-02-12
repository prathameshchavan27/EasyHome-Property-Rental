package com.prs.controller;

import org.springframework.http.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prs.dto.PropertyRequestDTO;
import com.prs.pojos.Property;
import com.prs.pojos.PropertyType;



@RestController
//@RequestMapping("/landlord/properties")
public class PropertiesController {

	@Autowired
	private com.prs.service.PropertyService propertyService;
	
    
	@PostMapping(value = "/landlord/properties/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPropertyWithImages(
            @RequestPart("property") String propertyJson,
            @RequestPart("images") List<MultipartFile> images) {
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
        	PropertyRequestDTO property = objectMapper.readValue(propertyJson, PropertyRequestDTO.class);
            // Save Property
        	System.out.println(property);
        	propertyService.addProperty(property.getTitle(), property.getDescription(), property.getAddress(), property.getCity(), property.getRent(),property.getPropertyType(), property.getOwnerId(), property.getAmenityIds(),images);
            
            return ResponseEntity.ok("Property and images added successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while saving property or images: " + e.getMessage());
        }
    } 
	
	@PutMapping(value = "/landlord/properties/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateProperty(@PathVariable Long id,
	        @RequestPart("property") String propertyJson,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images) {
	    try {
	        // Convert JSON string to DTO
	        ObjectMapper objectMapper = new ObjectMapper();
	        PropertyRequestDTO propertyDTO = objectMapper.readValue(propertyJson, PropertyRequestDTO.class);

	        // Call the service layer to update the property
	        propertyService.updateProperty(
	                id,
	                propertyDTO.getTitle(),
	                propertyDTO.getDescription(),
	                propertyDTO.getAddress(),
	                propertyDTO.getCity(),
	                propertyDTO.getRent(),
	                propertyDTO.getOwnerId(),
	                propertyDTO.getAmenityIds(),
	                images
	        );

	        return ResponseEntity.ok("Property updated successfully!");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error occurred while updating property: " + e.getMessage());
	    }
	}

    
    @GetMapping("/properties")
    public ResponseEntity<?> getAllProperties(){
    	return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
    }
    
    @GetMapping("/properties/filter")
    public ResponseEntity<List<Property>> getProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer minRent,
            @RequestParam(required = false) Integer maxRent,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) List<String> amenities) {
//        PropertyType type = PropertyType.valueOf(propertyType);
        List<Property> properties = propertyService.getFilteredProperties(city, minRent, maxRent, available, propertyType, amenities);
        return ResponseEntity.ok(properties);
    }

    
//
    @GetMapping("/property/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        
        if (property.isPresent()) {
            return ResponseEntity.ok(property.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @GetMapping("/landlord/properties/{id}")
    public ResponseEntity<?> getPropertiesByLid(@PathVariable Long id){
    	return ResponseEntity.status(HttpStatus.OK).body(propertyService.getPropertiesByLid(id));
    }
    
    @GetMapping("/properties/search")
    public ResponseEntity<?> searchProperty(@RequestParam String city){
    	return ResponseEntity.status(HttpStatus.OK).body(propertyService.searchProperty(city));
    }
    
    @DeleteMapping("/landlord/properties/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id){
    	return ResponseEntity.status(HttpStatus.OK).body(propertyService.deleteProperty(id));
    }
    
    

    
}
