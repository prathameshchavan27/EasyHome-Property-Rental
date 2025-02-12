import React, { useState } from "react";
import axios from "axios";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");
  const url = `${process.env.REACT_APP_API_URL}`;
  const handleForgotPassword = async () => {
    try {
      const response = await axios.post(`${url}/password/forgot-password?email=${email}`);
      setMessage(response.data);
    } catch (error) {
      setMessage("Error sending reset email!");
    }
  };

  return (
    <div className="container mt-5">
      <h2>Forgot Password</h2>
      <input
        type="email"
        className="form-control mb-2"
        placeholder="Enter your email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <button className="btn btn-primary" onClick={handleForgotPassword}>
        Send Reset Link
      </button>
      <p>{message}</p>
    </div>
  );
};

export default ForgotPassword;
