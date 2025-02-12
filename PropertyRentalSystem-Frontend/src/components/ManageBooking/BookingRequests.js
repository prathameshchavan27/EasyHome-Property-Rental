import axios from "axios";
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const BookingRequests = () => {
  const [requests, setRequests] = useState([]);
  const [paymentStatuses, setPaymentStatuses] = useState({});
  const [loading, setLoading] = useState(true);

  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("jwtToken");
  const navigate = useNavigate();
  const location = useLocation();
  const url = `${process.env.REACT_APP_API_URL}`;
  // Fetch Booking Requests
  const fetchRequests = async () => {
    try {
      setLoading(true);
      const response = await axios.get(
        `${url}/bookings/${userId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setRequests(response.data);
      setLoading(false);
    } catch (error) {
      console.error("Error fetching bookings:", error);
      setLoading(false);
    }
  };

  // Fetch Payment Status for Each Booking
  const fetchPaymentStatus = async (bookingId) => {
    try {
      const response = await axios.get(
        `${url}/payments/${bookingId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data.paymentStatus; // Return status
    } catch (error) {
      console.error("Error fetching payment status:", error);
      return "Pending"; // Default to pending if error occurs
    }
  };

  // Fetch all payment statuses and update state
  const updatePaymentStatuses = async () => {
    const statuses = {};
    for (let request of requests) {
      statuses[request.id] = await fetchPaymentStatus(request.id);
    }
    setPaymentStatuses(statuses);
  };

  useEffect(() => {
    fetchRequests();
  }, []);

  // Update Payment Status when returning from Payment Page
  useEffect(() => {
    if (requests.length > 0) {
      updatePaymentStatuses();
    }
  }, [requests, location]); // Runs when location changes (redirect from payment)

  // Handle Payment Navigation
  const handlePayment = (id, amount) => {
    navigate("/payment", {
      state: { request: { requestId: id, amount: amount } },
    });
  };

  return (
    <div className="container mt-5">
      <h2>Booking Requests</h2>
      {loading ? (
        <p>Loading requests...</p>
      ) : (
        <ul className="list-group">
          {requests.map((request) => (
            <li
              key={request.id}
              className="list-group-item d-flex justify-content-between align-items-center"
            >
              <div>
                <strong>Booking ID: {request.id}</strong>
                <p>Status: {request.status}</p>
              </div>
              <div>
                <p>
                  Payment Status: {paymentStatuses[request.id] || "Fetching..."}
                </p>
              </div>
              <div>
                {request.status === "APPROVED" &&
                  paymentStatuses[request.id] !== "succeeded" &&
                  paymentStatuses[request.id] !== "COMPLETED" && ( // Hide button for succeeded or completed payments
                    <button
                      className="btn btn-primary"
                      onClick={() => handlePayment(request.id, request.amount)}
                    >
                      Pay Now
                    </button>
                  )}
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default BookingRequests;
