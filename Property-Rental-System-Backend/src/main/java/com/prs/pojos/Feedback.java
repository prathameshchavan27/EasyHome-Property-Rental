package com.prs.pojos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "feedback")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "propertyId", nullable = false)
    private Long propertyId;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

//    

	public Feedback(Long propertyId, Long tenantId, String comments) {
		super();
		this.propertyId = propertyId;
		this.ownerId = tenantId;
		this.comments = comments;
		this.createdAt = LocalDateTime.now();
	}
}
