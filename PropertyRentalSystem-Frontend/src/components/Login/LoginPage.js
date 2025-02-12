import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css"; // Custom styling for the Login page
import { useAuth } from "../../Auth/AuthContext";

const Login = () => {
  const [formData, setFormData] = useState({ username: "", password: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const url = `${process.env.REACT_APP_API_URL}`;
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };
  const {isLogin,setIsLogin} = useAuth();
  const {role,setRole} = useAuth();
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(`${url}/login`, formData);

      if (response.status === 200) {
        const data = response.data;
        const token = data.token;
        setMessage("Login successful!");
        localStorage.setItem("jwtToken", token);
        localStorage.setItem("userId", data.user.id);
        localStorage.setItem("role",data.user.role);
        setRole(data.user.role);
        setIsLogin(true);
        if(data.user.role==='ADMIN'){
          console.log(data.user.role);
          navigate("/admin/user-management")
        }else{
          navigate("/home", { state: { user: data.user } });
        }
      } else {
        setMessage("Invalid credentials!");
      }
    } catch (error) {
      console.error("Error:", error);
      setMessage("Wrong username or password. Please try again.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h2>Login to Your Account</h2>
        {message && <p className="message">{message}</p>}
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label>Username</label>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
              className="input-field"
            />
          </div>
          <div className="input-group">
            <label>Password</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              className="input-field"
            />
          </div>
          <button type="submit" className="submit-btn">Login</button>
        </form>
        <p className="register-link">
        <a href="/forgot-password">forgot password? </a>
        </p>
        <p className="register-link">
          Don't have an account? <a href="/">Sign up</a>
        </p>
      </div>
    </div>
  );
};

export default Login;
