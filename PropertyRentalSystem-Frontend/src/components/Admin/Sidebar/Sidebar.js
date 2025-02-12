// Sidebar.jsx
import React from "react";
import { NavLink } from "react-router-dom";
import {
  FaUser,
  FaBuilding,
  FaClipboardList,
  FaCreditCard,
  FaCommentAlt,
} from "react-icons/fa";
import "./Sidebar.css"; // Custom CSS for sidebar styling

function Sidebar() {
  return (
    <div className="sidebar bg-dark text-white p-4">
      <h4 className="sidebar-title mb-4">Admin Dashboard</h4>
      <ul className="nav flex-column">
        <li className="nav-item">
          <NavLink
            to="/admin/user-management"
            className="nav-link text-white"
            activeclassname="active"
          >
            <FaUser className="me-2" /> User Management
          </NavLink>
        </li>
        <li className="nav-item">
          <NavLink
            to="/admin/property-management"
            className="nav-link text-white"
            activeclassname="active"
          >
            <FaBuilding className="me-2" /> Property Management
          </NavLink>
        </li>
        <li className="nav-item">
          <NavLink
            to="/admin/booking-management"
            className="nav-link text-white"
            activeclassname="active"
          >
            <FaClipboardList className="me-2" /> Booking Management
          </NavLink>
        </li>
        <li className="nav-item">
          <NavLink
            to="/admin/payment-management"
            className="nav-link text-white"
            activeclassname="active"
          >
            <FaCreditCard className="me-2" /> Payment Management
          </NavLink>
        </li>
        <li className="nav-item">
          <NavLink
            to="/admin/feedback-reports"
            className="nav-link text-white"
            activeclassname="active"
          >
            <FaCommentAlt className="me-2" /> Reports
          </NavLink>
        </li>
      </ul>
    </div>
  );
}

export default Sidebar;
