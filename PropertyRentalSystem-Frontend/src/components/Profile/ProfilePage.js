import React, { useState, useEffect } from "react";
import axios from "axios";
import "./ProfilePage.css"; // Optional: custom CSS if needed

const ProfilePage = () => {
  const [user, setUser] = useState(null);
  const [bookings, setBookings] = useState([]);
  const [loadingUser, setLoadingUser] = useState(true);
  const [loadingBookings, setLoadingBookings] = useState(true);
  const userId = localStorage.getItem("userId"); // Assuming the user ID is stored in localStorage
  const token = localStorage.getItem("jwtToken");
  const url = `${process.env.REACT_APP_API_URL}`;
  const fetchUser = async() => {
    try{
      const response = await axios.get(`${url}/users/${userId}`,{headers:{
        Authorization: `Bearer ${token}`
      }});
      console.log(response.data)
      setUser(response.data);
      setLoadingUser(false);
    }catch(error){
      console.log(error);
    }
  }

  const fetchUserBookings = async() => {
    try{
      const response = await axios.get(`${url}/bookings/${userId}`,{headers:{
        Authorization: `Bearer ${token}`
      }});
      setBookings(response.data)
      setLoadingBookings(false);
    }catch(error){
      console.log(error);
    }
  }


  useEffect(() => {
    // Fetch User Details
    fetchUser();
    fetchUserBookings();
  }, [userId]);

  return (
    <div className="profile-page container mt-4">
      {/* User Details Section */}
      <div className="user-details card mb-4">
        <div className="card-body">
          <h3 className="card-title">User Profile</h3>
          {loadingUser ? (
            <p>Loading user details...</p>
          ) : user ? (
            <div className="row">
              {/* <div className="col-md-3 text-center">
                <img
                  src={user.profilePicture || "default-profile.png"}
                  alt="Profile"
                  className="img-fluid rounded-circle profile-img"
                />
              </div> */}
              <div className="col-md-9">
                <p>
                  <strong>Name:</strong> {user.name}
                </p>
                <p>
                  <strong>Email:</strong> {user.email}
                </p>
                <p>
                  <strong>Phone:</strong> {user.phone}
                </p>
                {/* Add more fields as needed */}
              </div>
            </div>
          ) : (
            <p>User details not available.</p>
          )}
        </div>
      </div>

      {/* Booking Details Section */}
      <div className="booking-details card">
        <div className="card-body">
          <h3 className="card-title">Your Bookings</h3>
          {loadingBookings ? (
            <p>Loading your bookings...</p>
          ) : bookings && bookings.length > 0 ? (
            <div className="table-responsive">
              <table className="table table-striped">
                <thead>
                  <tr>
                    <th>Booking ID</th>
                    <th>Property</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {bookings.map((booking) => (
                    <tr key={booking.id}>
                      <td>{booking.id}</td>
                      <td>{booking.propertyName || "Property Name"}</td>
                      <td>{booking.startDate}</td>
                      <td>{booking.endDate}</td>
                      <td>{booking.status}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <p>You have no bookings.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
