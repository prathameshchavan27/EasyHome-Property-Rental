
package com.prs.pojos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "property_amenities")
@IdClass(PropertyAmenityId.class)
public class PropertyAmenity {

	@Id
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "property_id", nullable = false)
	private Property property;


    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;
}
