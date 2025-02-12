package com.prs.pojos;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Images {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long propertyId; // This links the image to a specific property

    private String imageUrl;

    private LocalDateTime uploadedAt;

	public Images(Long propertyId, String imageUrl, LocalDateTime uploadedAt) {
		super();
		this.propertyId = propertyId;
		this.imageUrl = imageUrl;
		this.uploadedAt = uploadedAt;
	}
    
}
