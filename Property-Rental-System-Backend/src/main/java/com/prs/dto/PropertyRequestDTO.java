package com.prs.dto;
//
//import java.util.Set;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class PropertyRequestDTO {
//    private String title;
//    private String description;
//    private String address;
//    private String city;
//    private double rent;
//    private Long ownerId;
//    private Set<Long> amenitites;  // List of selected amenity IDs
//}

import java.util.List;

//import com.prs.pojos.PropertyType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyRequestDTO {
    private String title;
    private String description;
    private String address;
    private String city;
    private double rent;
    private String propertyType;
    private Long ownerId;
    private List<Long> amenityIds;  // List of selected amenity IDs
}
