package com.prs.pojos;

import java.io.Serializable;
import java.util.Objects;

public class PropertyAmenityId implements Serializable {
    private Long property;
    private Long amenity;

    public PropertyAmenityId() {}

    public PropertyAmenityId(Long property, Long amenity) {
        this.property = property;
        this.amenity = amenity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyAmenityId that = (PropertyAmenityId) o;
        return Objects.equals(property, that.property) &&
               Objects.equals(amenity, that.amenity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, amenity);
    }
}
