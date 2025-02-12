import React, { useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";

const ResetPassword = () => {
  const [newPassword, setNewPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const location = useLocation();
  const token = new URLSearchParams(location.search).get("token"); // Get token from URL
  const url = `${process.env.REACT_APP_API_URL}`;
  const handleResetPassword = async () => {
    try {
      const response = await axios.post(`${url}/password/reset-password?token=${token}`, { newPassword });
      setMessage(response.data);
      setTimeout(() => navigate("/login"), 2000); // Redirect to login after success
    } catch (error) {
      setMessage("Error resetting password!");
    }
  };

  return (
    <div className="container mt-5">
      <h2>Reset Password</h2>
      <input
        type="password"
        className="form-control mb-2"
        placeholder="Enter new password"
        value={newPassword}
        onChange={(e) => setNewPassword(e.target.value)}
      />
      <button className="btn btn-primary" onClick={handleResetPassword}>
        Reset Password
      </button>
      <p>{message}</p>
    </div>
  );
};

export default ResetPassword;
