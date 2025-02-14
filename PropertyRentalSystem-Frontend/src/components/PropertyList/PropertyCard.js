
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./PropertyCard.css";
import { useAuth } from "../../Auth/AuthContext";
import axios from "axios";

const PropertyCard = ({ property, deleteProperty }) => {
  // const property = props.property;
  // const deleteProperty = props.deleteProperty;
  
  const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 3000,
  };



  const {isLogin,setIsLogin} = useAuth();
  const url = `/property/${property.id}`;
  const token = localStorage.getItem("jwtToken");
  const navigate = useNavigate();
  const url_backend = `${process.env.REACT_APP_API_URL}`;
 console.log(property.available)

  return (
    <div className="property-card">
      {/* Image Slider */}
      <div className="property-image-slider">
        {property.images && property.images.length > 0 ? (
          <Slider {...sliderSettings}>
            {property.images.map((image, index) => (
              <div key={index} className="image-slide">
                {index === 0 && <span className="live-badge">LIVE</span>}
                <img src={`https://raw.githubusercontent.com/prathameshchavan27/EasyHome-Property-Rental/refs/heads/master/Property-Rental-System-Backendsrc/main/resources/images/${image.imageUrl}`} alt="Property"/>
              </div>
            ))}
          </Slider>
        ) : (
          <p className="no-images">No images available</p>
        )}
      </div>

      {/* Property Details */}
      <div className="property-details">
        <h2 className="property-title">{property.title}</h2>
        <p className="property-location">
          📍 {property.city} &bull; <Link to="#">Explore Nearby</Link>
        </p>

        {/* Pricing Section */}
        <div className="property-pricing">
          <div className="price-item">
            <strong className="price">₹{property.rent}</strong>
            <p>Rent</p>
          </div>
          <div className="price-item">
            <strong className="price">₹{property.rent*2}</strong>
            <p>Deposit</p>
          </div>
          <div className="price-item">
            <strong className="price">{property.builtup?property.builtup:800} sqft</strong>
            <p>Built-up Area</p>
          </div>
        </div>

        {/* Features */}
        <div className="property-features">
          <div className="feature-item">
            <span className="feature-icon">🛋️</span>
            <p>{property.propertyType || "Unfurnished"}</p>
          </div>
          {/* <div className="feature-item">
            <span className="feature-icon">👨‍👩‍👧</span>
            <p>{property.tenants || "Family Preferred"}</p>
          </div> */}
          {/* <div className="feature-item">
            <span className="feature-icon">🏢</span>
            <p>{property.type || "1 BHK"}</p>
          </div> */}
          <div className="feature-item">
            <span className="feature-icon">🔑</span>
            <p>{property.availability || "Ready to Move"}</p>
          </div>
        </div>

        {/* Actions */}
        <div className="property-actions">

          <Link to={localStorage.getItem("userId")?url:"/login"} className="contact-btn">View Details</Link>
          {localStorage?.getItem("userId") == property?.owner?.id
          ? (property.id && <button className="btn btn-danger" onClick={() => deleteProperty(property.id)}>Delete</button>)
          : null}
          {!property.available && <button disabled className="btn btn-danger">Booked</button>}
      </div>
    </div>
    </div>
  );
};

export default PropertyCard;
