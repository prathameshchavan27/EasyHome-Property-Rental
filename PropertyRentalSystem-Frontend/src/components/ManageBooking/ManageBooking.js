import { useEffect, useState } from "react";
import axios from "axios";
import "./ManageBooking.css"; // Import CSS file

const ManageBooking = () => {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const token = localStorage.getItem("jwtToken");
    const url = `${process.env.REACT_APP_API_URL}`;
    const fetchBookings = async () => {
        try {
            const response = await axios.get(`${url}/bookings/pending`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setBookings(response.data);
        } catch (error) {
            setError("Failed to fetch bookings. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchBookings();
    }, []);

    const handleApprove = async (bookingId) => {
        try {
            const response = await axios.put(`${url}/bookings/approve/${bookingId}`,{}, {
                headers: { Authorization: `Bearer ${token}` },
            });
            console.log(response)
            alert("Booking Approved");
            fetchBookings(); // Refresh list after approval
        } catch (error) {
            alert("Failed to approve booking");
        }
    };

    const handleReject = async (bookingId) => {
        try {
            await axios.put(`${url}/bookings/${bookingId}/reject`, {}, {
                headers: { Authorization: `Bearer ${token}` },
            });
            alert("Booking Rejected");
            fetchBookings(); // Refresh list after rejection
        } catch (error) {
            alert("Failed to reject booking");
        }
    };

    if (loading) return <p className="loading">Loading bookings...</p>;
    if (error) return <p className="error">{error}</p>;

    return (
        <div className="container">
            <h2>Pending Bookings</h2>
            <table className="booking-table">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Tenant</th>
                        <th>Property</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {bookings.map((booking) => (
                        <tr key={booking.id}>
                            <td>{booking.id}</td>
                            <td>{booking.tenantId}</td>
                            <td>{booking.propertyId}</td>
                            <td>
                                <button className="approve-btn" onClick={() => handleApprove(booking.id)}>Approve</button>
                                <button className="reject-btn" onClick={() => handleReject(booking.id)}>Reject</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ManageBooking;
