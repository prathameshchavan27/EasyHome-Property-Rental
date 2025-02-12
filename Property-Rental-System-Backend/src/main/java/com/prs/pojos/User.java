package com.prs.pojos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users") // Explicit table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 15) // Limiting phone number length
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @JsonIgnore
    @OneToMany(mappedBy = "owner",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Property> properties;
    
    @JsonIgnore
    @OneToMany(mappedBy = "ownerId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Feedback> feedbacks;

	public User(Long id) {
		super();
		this.id = id;
	}
	
	 public void addProperty(Property property) {
	        properties.add(property);
	        property.setOwner(this);
	    }

	    // Helper method to remove a property
	    public void removeProperty(Property property) {
	        properties.remove(property);
	        property.setOwner(null);
	    }

//	    public User(Long id) {
//	        this.id = id;
//	    }
    
    
}
