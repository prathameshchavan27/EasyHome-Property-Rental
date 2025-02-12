package com.prs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.pojos.Images;

public interface ImageRepository extends JpaRepository<Images, Long> {
	Images findByImageUrl(String imageUrl);

	void deleteByPropertyId(Long id);
}
