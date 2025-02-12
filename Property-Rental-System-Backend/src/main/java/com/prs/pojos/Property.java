package com.prs.pojos;



import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "properties")
@Getter
@Setter
@ToString(exclude = {"amenities","images"})
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String address;
    private String city;
    private double rent;
    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyType propertyType;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "owner", nullable = true) // Foreign key column
    private User owner;

    @Transient
    private Long ownerId; // Used only for accepting input from the API

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean available;

    @OneToMany(mappedBy = "propertyId", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Images> images;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
        name = "property_amenities",
        joinColumns = @JoinColumn(name = "property_id"),
        inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    @OneToMany(mappedBy = "propertyId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;
    

    public Property(String title, String description, String address, String city, double rent,String type, User owner, List<Amenity> amenities) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.city = city;
        this.rent = rent;
        this.owner = owner; // Assign the User object directly
        this.amenities = amenities;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.propertyType = PropertyType.valueOf(type.toUpperCase());
        this.available = true;
    }

	public Property(Long propertyId) {
		// TODO Auto-generated constructor stub
		this.id = propertyId;
	}


	
	
}
