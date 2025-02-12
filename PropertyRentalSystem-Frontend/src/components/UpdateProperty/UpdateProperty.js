import React, { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
// import "./AddProperty.css";

const UpdateProperty = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const token = localStorage.getItem("jwtToken");
  const url = `${process.env.REACT_APP_API_URL}`;
  // Extract property data from location state
  const updateProperty = location.state || {};
  
  // Extract only amenity IDs
  const existingAmenityIds = updateProperty.amenities?.map(a => a.id) || [];

  const [formData, setFormData] = useState({
    id: updateProperty.id || "",
    title: updateProperty.title || "",
    description: updateProperty.description || "",
    address: updateProperty.address || "",
    city: updateProperty.city || "",
    rent: updateProperty.rent || "",
    selectedAmenities: existingAmenityIds,
    newAmenity: ""
  });

  const [images, setImages] = useState([]);
  const [amenities, setAmenities] = useState([]);

  useEffect(() => {
    fetchAmenities();
  }, []);

  const fetchAmenities = async () => {
    try {
      const response = await axios.get(`${url}/landlord/all`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAmenities(response.data);
    } catch (error) {
      console.error("Error fetching amenities:", error);
      navigate("/login");
    }
  };

  const handleAmenityAdd = async () => {
    if (!formData.newAmenity.trim()) return;
    try {
      const response = await axios.post(
        `${url}/landlord/addAmenities`,
        { name: formData.newAmenity },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setAmenities([...amenities, response.data]);
      fetchAmenities();
      setFormData((prevState) => ({ ...prevState, newAmenity: "" }));
    } catch (error) {
      console.error("Error adding amenity:", error);
    }
  };

  const handleImageUpload = (event) => {
    setImages((prevState) => [...prevState, ...event.target.files]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const { title, description, address, city, rent, selectedAmenities } = formData;

    const form = new FormData();
    const property = {
      title,
      description,
      address,
      city,
      rent,
      ownerId: localStorage.getItem("userId"), // Ensure owner ID is sent
      amenityIds: selectedAmenities
    };

    form.append("property", JSON.stringify(property));
    images.forEach((image) => form.append("images", image));
    // console.log(form.values);
    try {
      await axios.put(`${url}/landlord/properties/update/${updateProperty.id}`, form, {
        headers: { 
          "Content-Type": "multipart/form-data", 
          Authorization: `Bearer ${token}` 
        },
      });
      alert("Property updated successfully!");
      navigate("/properties"); // Redirect to properties list
    } catch (error) {
      console.error("Error updating property:", error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  return (
    <div className="add-property-container">
      <h2>Update Property</h2>
      <form onSubmit={handleSubmit} className="property-form">
        <input 
          type="text" 
          placeholder="Title" 
          name="title" 
          value={formData.title} 
          onChange={handleInputChange} 
          required 
        />
        <textarea 
          placeholder="Description" 
          name="description" 
          value={formData.description} 
          onChange={handleInputChange} 
          required 
        />
        <input 
          type="text" 
          placeholder="Address" 
          name="address" 
          value={formData.address} 
          onChange={handleInputChange} 
          required 
        />
        <input 
          type="text" 
          placeholder="City" 
          name="city" 
          value={formData.city} 
          onChange={handleInputChange} 
          required 
        />
        <input 
          type="number" 
          placeholder="Rent (₹)" 
          name="rent" 
          value={formData.rent} 
          onChange={handleInputChange} 
          required 
        />

        <label>Amenities:</label>
        <div className="amenities-checkboxes">
          {amenities.map((amenity) => (
            <div key={amenity.id} className="amenity-checkbox">
              <input
                type="checkbox"
                id={`amenity-${amenity.id}`}
                name="selectedAmenities"
                value={amenity.id}
                checked={formData.selectedAmenities.includes(amenity.id)}
                onChange={(e) => {
                  const { checked, value } = e.target;
                  const amenityId = Number(value);
                  setFormData((prevState) => ({
                    ...prevState,
                    selectedAmenities: checked
                      ? [...prevState.selectedAmenities, amenityId]
                      : prevState.selectedAmenities.filter((id) => id !== amenityId)
                  }));
                }}
              />
              <label htmlFor={`amenity-${amenity.id}`}>{amenity.name}</label>
            </div>
          ))}
        </div>

        <div className="amenity-add">
          <input 
            type="text" 
            placeholder="New Amenity" 
            value={formData.newAmenity} 
            onChange={(e) => setFormData({ ...formData, newAmenity: e.target.value })} 
          />
          <button type="button" onClick={handleAmenityAdd}>Add</button>
        </div>

        <label>Upload New Images (Optional):</label>
        <input 
          type="file" 
          multiple 
          accept="image/*" 
          onChange={handleImageUpload} 
        />

        <button type="submit" className="submit-btn">Update Property</button>
      </form>
    </div>
  );
};

export default UpdateProperty;
