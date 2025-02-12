
import React, { useState } from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements, useStripe, useElements, CardElement } from "@stripe/react-stripe-js";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

const stripePromise = loadStripe("pk_test_51QoJxED6HUTtJZosiqzKxWcoEXpNxs7xusiG57XMx3jWc6LzOQBay1ydOkDDugOI3fPGziqVSGDMLpfHx0R2WOaM00VkLrZJqQ");


const cardStyle = {
  style: {
    base: {
      fontSize: "16px",
      color: "#424770",
      backgroundColor: "#f8f9fa",
      "::placeholder": { color: "#aab7c4" },
      padding: "10px",
      border: "1px solid #ced4da",
      borderRadius: "5px",
    },
    invalid: { color: "#9e2146" },
  },
};

const CheckoutForm = () => {
    const stripe = useStripe();
    const elements = useElements();
    const [loading, setLoading] = useState(false);
    const location = useLocation();
    const bookingId = location.state.request.requestId;
    const property = JSON.parse(sessionStorage.getItem("property"));
    const amount = property.rent;
    const token = localStorage.getItem("jwtToken");
    const navigate = useNavigate();
    const url = `${process.env.REACT_APP_API_URL}`;
    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);

        const response = await axios.post(
            `${url}/payments/create?bookingId=${bookingId}&amount=${amount}`,
            {},
            { headers: { Authorization: `Bearer ${token}` } }
          );
          
        // const clientSecret = await response.text();
          console.log("payment",response.data);
        const result = await stripe.confirmCardPayment(response.data, {
            payment_method: { card: elements.getElement(CardElement) },
        });

        if (result.error) {
            alert("Payment Failed: " + result.error.message);
        } else {
            console.log(result)
            await axios.post(`${url}/payments/stripe-webhook`, {
                data: { id: result.paymentIntent.id ,pid: property.id}// Ensure proper payload format
            },{ headers: { Authorization: `Bearer ${token}` } }  );
        
            alert("Payment Successful!");
            navigate("/requests",{state:{paymentStatus: result.paymentIntent.status,bid:bookingId}});
        }
        setLoading(false);
    };

    return (
        <form onSubmit={handleSubmit} style={{ maxWidth: "400px", margin: "auto", padding: "20px", border: "1px solid #ddd", borderRadius: "10px", backgroundColor: "#fff", boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)" }}>
            <h3 style={{ textAlign: "center", color: "#007bff" }}>Secure Payment</h3>
            <div style={{ marginBottom: "15px", padding: "10px", borderRadius: "5px", backgroundColor: "#f8f9fa" }}>
                <CardElement options={cardStyle} />
            </div>
            <button type="submit" disabled={!stripe || loading} style={{ width: "100%", padding: "10px", border: "none", borderRadius: "5px", backgroundColor: "#007bff", color: "#fff", fontSize: "16px", cursor: "pointer" }}>
                {loading ? "Processing..." : `Pay ₹${amount}`}
            </button>
        </form>
    );
};

const StripePayment = ({ bookingId, amount }) => (
    <Elements stripe={stripePromise}>
        <CheckoutForm bookingId={bookingId} amount={amount} />
    </Elements>
);

export default StripePayment;
