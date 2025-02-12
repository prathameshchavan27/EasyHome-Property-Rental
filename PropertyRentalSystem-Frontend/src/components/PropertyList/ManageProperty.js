import axios from "axios";
import React, { useEffect, useState } from "react";
import PropertyCard from "./PropertyCard";

export default function ManageProperty() {
  const [properties, setProperties] = useState([]);
  const token = localStorage.getItem("jwtToken");
  const id = localStorage.getItem("userId");
  const url = `${process.env.REACT_APP_API_URL}`;
  const fetchPropertiesData = async () => {
    try {
      const response = await axios.get(
        `${url}/landlord/properties/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setProperties(response.data);
    } catch (error) {
      console.log(error);
    }
  };
  const deleteProperty = async (id) => {
    console.log("delete", id);
    try {
      const response = await axios.delete(
        `${url}/landlord/properties/${id}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      console.log(response);
    } catch (error) {
      console.error("Error fetching amenities:", error);
    }
    fetchPropertiesData();
  };

  useEffect(() => {
    fetchPropertiesData();
  }, []);
  return (
    <div className="properties-container">
      <h2 className="title">Explore Properties</h2>
      <div className="property-grid">
        {properties.map((property) => (
          <PropertyCard
            key={property.id}
            property={property}
            deleteProperty={() => deleteProperty(property.id)}
          />
        ))}
      </div>
    </div>
  );
}
