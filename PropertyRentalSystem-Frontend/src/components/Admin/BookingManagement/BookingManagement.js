import axios from "axios";
import React, { useState, useEffect } from "react";

function BookingManagement() {
  const [bookings, setBookings] = useState([]);
const token = localStorage.getItem('jwtToken');
  // Fetch bookings from the API
  const fetchBookings = async () => {
    try{
        const response = await axios.get(`http://localhost:8081/bookings`,{headers:{
            Authorization: `Bearer ${token}`
        }});
        console.log(response.data)
        setBookings(response.data);
    }catch(error){
        console.log(error);
    }   
  }

  useEffect(() => {
   fetchBookings(); 
    // fetch(`http://localhost:8081/bookings`)
    //   .then(response => response.json())
    //   .then(data =>{console.log(data); setBookings(data)})
    //   .catch(error => console.error("Error fetching bookings:", error));
  }, []);

  return (
    <div>
      <h3>Booking Management</h3>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Tenant</th>
            <th>Property</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {bookings.map(booking => (
            <tr key={booking.id}>
              <td>{booking.tenantId}</td>
              <td>{booking.propertyId}</td>
              <td>{booking.status}</td>
              <td>
                <button className="btn btn-success btn-sm">Approve</button>
                <button className="btn btn-danger btn-sm">Reject</button>
                <button className="btn btn-info btn-sm">Cancel</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {/* <button onClick={handleDownload} className="btn btn-info btn-sm">Download Report</button> */}
    </div>
  );
}

export default BookingManagement;
