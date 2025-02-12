// ProtectedRoute.jsx
import React from "react";
import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = ({ allowedRole }) => {
  const role = localStorage.getItem("role");

  // If the user role is allowed, render the nested routes (Outlet),
  // otherwise redirect to the forbidden page.
  return role === allowedRole ? <Outlet /> : <Navigate to="/forbidden" replace />;
};

export default ProtectedRoute;
