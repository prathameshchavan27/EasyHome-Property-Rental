
import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import PropertyCard from "./PropertyCard";
import "./PropertiesList.css";
import { useAuth } from "../../Auth/AuthContext";
import FilterPanel from "./Filter/FilterPanel";

const PropertiesList = () => {
  const [loading, setLoading] = useState(true);
  const [properties, setProperties] = useState([]);
  const [filteredProperties, setFilteredProperties] = useState([]);
  const [filterPane, setfilterPane] = useState(false);
  // const [propertiesg, setPropertiesg] = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const searchText = location.state;
  const url_backend = `${process.env.REACT_APP_API_URL}`;
  const [filters, setFilters] = useState({
    city: searchText?searchText:"",
    minRent: "",
    maxRent: "",
    available: "", // "true" or "false"
    propertyType: "", // Furnished, Unfurnished, etc.
    amenities: [],
  });

  const token = localStorage.getItem("jwtToken");
  // Function to delete a property
  const deleteProperty = async (id) => {
    try {
      await axios.delete(`${url_backend}/landlord/properties/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchPropertiesData(); // Refresh list after deletion
    } catch (error) {
      console.error("Error deleting property:", error);
    }
  };

  // Fetch properties based on filters
  const fetchPropertiesData = async () => {
    setLoading(true);
    let url = `${url_backend}/properties/filter?`;

    // Append filters as query params
    if (filters.city) url += `city=${filters.city}&`;
    if (filters.minRent) url += `minRent=${filters.minRent}&`;
    if (filters.maxRent) url += `maxRent=${filters.maxRent}&`;
    if (filters.available !== "") url += `available=${filters.available}&`;
    if (filters.propertyType) url += `propertyType=${filters.propertyType}&`;
    if (filters.amenities.length > 0) url += `amenities=${filters.amenities.join(",")}&`;

    try {
      const response = await axios.get(url);
      if (response.status === 200) {
        setProperties(response.data);
        // setPropertiesg(response.data);
        setFilteredProperties(response.data);
      }
    } catch (error) {
      console.error("Error fetching properties:", error);
    } finally {
      setLoading(false);
    }
  };

  // Fetch properties on location change
  useEffect(() => {
    fetchPropertiesData();
  }, [location]);

  // Function to apply filters manually
  const applyFilters = () => {
    fetchPropertiesData();
  };
  const clearFilters = () => {
    setFilters({
      city: '',
      minRent: '',
      maxRent: '',
      available: '',
      propertyType: '',
      amenities: []
    });
    fetchPropertiesData();
  };

  return (
    <div className="properties-page">
      {/* Sticky Left Panel (Filters) */}

      {/* Right Panel - Properties List */}
      <div style={{height: 'fit-content'}}>
      <FilterPanel
        filters={filters}
        setFilters={setFilters}
        applyFilters={applyFilters}
        clearFilters={clearFilters}
      />
      </div>
      <div className="properties-container">
        <h2 className="title">Explore Properties</h2>

        {loading ? (
          <div className="loading">Loading properties...</div>
        ) : properties.length === 0 ? (
          <div className="no-properties">No properties available</div>
        ) : (
          <div className="property-grid">
            {filteredProperties.map((property) => (
              <PropertyCard
                key={property.id}
                property={property}
                deleteProperty={() => deleteProperty(property.id)}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default PropertiesList;
