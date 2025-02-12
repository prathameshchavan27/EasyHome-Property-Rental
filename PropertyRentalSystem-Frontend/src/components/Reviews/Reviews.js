import React, { useState } from "react";
import axios from "axios";
import "./Reviews.css"; // Create a separate CSS file for styling
import { useNavigate } from "react-router-dom";

const Reviews = ({ propertyId, feedbacks }) => {

  const [review, setReview] = useState("");
  const token = localStorage.getItem("jwtToken");
    const navigate = useNavigate();
    const url = `${process.env.REACT_APP_API_URL}`;

  const handleReviewSubmit = async () => {
    try {
      await axios.post(
        `${url}/property/${propertyId}/feedback`,
        { userId:localStorage.getItem("userId"), comments: review },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setReview("");
      alert("Review submitted successfully");
      
    } catch (error) {
      console.error("Error submitting review:", error);
    }
  };

  return (
    <div className="reviews-container">
      <h2>📝 Reviews</h2>
      {feedbacks?.length > 0 ? (
        feedbacks.map((feedback) => (
          <div key={feedback.id} className="review-card">
            <div className="review-header">
              <span className="review-date">{new Date(feedback.createdAt).toLocaleDateString()}</span>
            </div>
            <p className="review-comment">{feedback.comments}</p>
          </div>
        ))
      ) : (
        <p>No reviews available</p>
      )}

      <div className="review-input">
        <h3>Add a Review</h3>
        {/* <input
          type="number"
          min="1"
          max="5"
          value={rating}
          onChange={(e) => setRating(e.target.value)}
          placeholder="Rating (1-5)"
        /> */}
        <textarea
          value={review}
          onChange={(e) => setReview(e.target.value)}
          placeholder="Write your review..."
        ></textarea>
        <button onClick={handleReviewSubmit}>Submit Review</button>
      </div>
    </div>
  );
};

export default Reviews;
