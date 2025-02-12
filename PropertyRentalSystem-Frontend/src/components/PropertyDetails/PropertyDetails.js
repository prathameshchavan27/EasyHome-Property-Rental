// import React, { useEffect, useState } from "react";
// import { useNavigate, useParams } from "react-router-dom";
// import Slider from "react-slick";
// import axios from "axios";
// import "slick-carousel/slick/slick.css";
// import "slick-carousel/slick/slick-theme.css";
// import "./PropertyDetails.css"; // Import new CSS file

// const PropertyDetails = () => {
//   const { propertyId } = useParams();
//   const [property, setProperty] = useState(null);
//   const [loading, setLoading] = useState(true);
//   const token = localStorage.getItem("jwtToken");
//   const navigate = useNavigate();

//   useEffect(() => {
//     const fetchPropertyData = async () => {
//       try {
//         const response = await axios.get(`http://localhost:8081/property/${propertyId}`, {
//           headers: { Authorization: `Bearer ${token}` },
//         });
//         setProperty(response.data);
//       } catch (error) {
//         console.error("Error:", error);
//       } finally {
//         setLoading(false);
//       }
//     };

//     fetchPropertyData();
//   }, [propertyId, token]);

//   if (loading) return <div className="loading">Loading...</div>;
//   if (!property) return <div className="error">Property not found</div>;

//   const updateProperty = () => {
//     navigate("/update-property", { state: property });
//   };

//   const sliderSettings = {
//     dots: true,
//     infinite: true,
//     speed: 500,
//     slidesToShow: 1,
//     slidesToScroll: 1,
//     autoplay: true,
//     autoplaySpeed: 2500,
//   };

//   return (
//     <div className="new-property-container">
//       {/* Image Gallery */}
//       <div className="image-gallery">
//         {property.images && property.images.length > 0 ? (
//           <Slider {...sliderSettings} className="property-slider">
//             {property.images.map((image, index) => (
//               <div key={index} className="slider-image">
//                 <img src={`http://localhost:8081/${image.imageUrl}`} alt={`Property ${index + 1}`} />
//               </div>
//             ))}
//           </Slider>
//         ) : (
//           <p className="no-images">No images available</p>
//         )}
//       </div>

//       {/* Property Details */}
//       <div className="property-details">
//         <h1 className="property-title">{property.title}</h1>
//         <p className="property-description">{property.description}</p>

//         <div className="property-info">
//           <p><strong>📍 Location:</strong> {property.address}, {property.city}</p>
//           <p><strong>💰 Rent:</strong> <span className="rent-price">₹{property.rent}</span></p>
//         </div>

//         {/* Amenities */}
//         <div className="property-amenities">
//           <h2>🏡 Amenities</h2>
//           <ul>
//             {property.amenities && property.amenities.length > 0 ? (
//               property.amenities.map((amenity) => (
//                 <li key={amenity.id} className="amenity-item">✅ {amenity.name}</li>
//               ))
//             ) : (
//               <p>No amenities available</p>
//             )}
//           </ul>
//         </div>

//         {/* Reviews */}
//         <div className="property-reviews">
//           <h2>📝 Reviews</h2>
//           {property.feedbacks && property.feedbacks.length > 0 ? (
//             property.feedbacks.map((feedback) => (
//               <div key={feedback.id} className="review-card">
//                 <div className="review-header">
//                   <span className="review-rating">⭐ {feedback.rating}/5</span>
//                   <span className="review-date">{new Date(feedback.createdAt).toLocaleDateString()}</span>
//                 </div>
//                 <p className="review-comment">{feedback.comments}</p>
//               </div>
//             ))
//           ) : (
//             <p>No reviews available</p>
//           )}
//         </div>

//         {/* Action Buttons */}
//         <div className="action-buttons">
//           <button className="contact-button">📞 Contact Owner</button>
//           {localStorage.getItem("role") === "LANDLORD" && (
//             <button onClick={updateProperty} className="edit-button">Edit Property</button>
//           )}
//         </div>
//       </div>
//     </div>
//   );
// };

// export default PropertyDetails;
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Slider from "react-slick";
import axios from "axios";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./PropertyDetails.css";
import Reviews from "../Reviews/Reviews";

const PropertyDetails = () => {
  const { propertyId } = useParams();
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);
  const token = localStorage.getItem("jwtToken");
  const navigate = useNavigate();
  const [feedbacks,setFeedbacks] = useState([])
  const url = `${process.env.REACT_APP_API_URL}`;
  const fetchFeedbacks = async () => {
    try{
      const response = await axios.get(`${url}/properties/${propertyId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setFeedbacks(response.data);
    }catch (error) {
      console.error("Error:", error);
    }
  }

  useEffect(() => {
    const fetchPropertyData = async () => {
      try {
        const response = await axios.get(`${url}/property/${propertyId}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setProperty(response.data);
      } catch (error) {
        console.error("Error:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchPropertyData();
    fetchFeedbacks();
  }, [propertyId, token]);

  if (loading) return <div className="loading">Loading...</div>;
  if (!property) return <div className="error">Property not found</div>;

  const updateProperty = () => {
    navigate("/update-property", { state: property });
  };

  const book = () =>{
    console.log(property)
    sessionStorage.setItem("property",JSON.stringify(property))
    navigate('/book-property',{state:property});
  }

  const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2500,
  };

  return (
    <>
    <div className="new-property-container">
      <div className="image-gallery">
        {property.images && property.images.length > 0 ? (
          <Slider {...sliderSettings} className="property-slider">
            {property.images.map((image, index) => (
              <div key={index} className="slider-image">
                <img src={`${url}/images/${image.imageUrl}`} alt={`Property ${index + 1}`} />
              </div>
            ))}
          </Slider>
        ) : (
          <p className="no-images">No images available</p>
        )}
      </div>

      <div className="property-details">
        <h1 className="property-title">{property.title}</h1>
        <p className="property-description">{property.description}</p>

        <div className="property-info">
          <p><strong>📍 Location:</strong> {property.address}, {property.city}</p>
          <h4><strong>💰 Rent:</strong> <span className="rent-price">₹{property.rent}</span></h4>
        </div>

        <div className="property-amenities">
          <h2>🏡 Amenities</h2>
          <ul>
            {property.amenities && property.amenities.length > 0 ? (
              property.amenities.map((amenity) => (
                <li key={amenity.id} className="amenity-item">✅ {amenity.name}</li>
              ))
            ) : (
              <p>No amenities available</p>
            )}
          </ul>
        </div>

        <div className="action-buttons">
          
          {localStorage.getItem("role") === "LANDLORD" ? (
            <button onClick={updateProperty} className="edit-button">Edit Property</button>
          ):<button disabled={!property.available} onClick={book} className="contact-button">Book </button>}
        </div>
      </div>

    </div>
      <Reviews propertyId={propertyId} feedbacks={feedbacks} />
    </>
  );
};

export default PropertyDetails;
