package com.prs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prs.pojos.Property;
import com.prs.pojos.PropertyType;
import com.prs.pojos.User;




public interface PropertyRepository extends JpaRepository<Property, Long> {
	List<Property> findByCity(String city);
	List<Property> findByOwner(User owner);
	
	@Query("SELECT p FROM Property p WHERE "
		     + "(:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))) "
		     + "AND (:minRent IS NULL OR p.rent >= :minRent) "
		     + "AND (:maxRent IS NULL OR p.rent <= :maxRent) "
		     + "AND (:available IS NULL OR p.available = :available) "
		     + "AND (:propertyType IS NULL OR p.propertyType = :propertyType)")
		List<Property> findFilteredProperties(
		        @Param("city") String city,
		        @Param("minRent") Integer minRent,
		        @Param("maxRent") Integer maxRent,
		        @Param("available") Boolean available,
		        @Param("propertyType") PropertyType propertyType
		);

}
