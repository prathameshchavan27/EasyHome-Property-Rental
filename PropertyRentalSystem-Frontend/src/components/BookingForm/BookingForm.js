// import { useState } from "react";
// import axios from "axios";
// import { useLocation, useNavigate } from "react-router-dom";

// const BookingForm = () => {
//     const location = useLocation();
//     const pid = location.state.id;
//     console.log(pid);
//     const [propertyId, setPropertyId] = useState(pid);
//     const tenantId = localStorage.getItem("userId")
//     const [startDate, setStartDate] = useState("");
//     const [endDate, setEndDate] = useState("");
//     const token = localStorage.getItem("jwtToken");
//     const navigate = useNavigate();

//     const handleSubmit = async(event) => {
//         event.preventDefault();

//         const bookingData = { propertyId, tenantId, startDate, endDate };
//         try{
//             const response = axios.post("http://localhost:8081/bookings/create", bookingData,{headers:{
//                 Authorization: `Bearer ${token}`
//             }});
//             console.log(response)
//             navigate('/properties')
//         }catch(error){
//             console.log(error);
//         }
           
//     };

//     return (
//         <form onSubmit={handleSubmit}>
//             <h2>Book a Property</h2>
//             <label>Property ID:</label>
//             <input type="number" value={propertyId} onChange={(e) => setPropertyId(e.target.value)} required />

//             <label>Start Date:</label>
//             <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} required />

//             <label>End Date:</label>
//             <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} required />

//             <button type="submit">Book Now</button>
//         </form>
//     );
// };

// export default BookingForm;
import { useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";

const BookingForm = () => {
    const location = useLocation();
    const pid = location.state?.id || "";
    const [propertyId, setPropertyId] = useState(pid);
    const tenantId = localStorage.getItem("userId");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const token = localStorage.getItem("jwtToken");
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const url = `${process.env.REACT_APP_API_URL}`;
    const handleSubmit = async (event) => {
        event.preventDefault();
        setError(null); // Reset error state

        if (!propertyId || !startDate || !endDate) {
            setError("All fields are required.");
            return;
        }

        const bookingData = { propertyId, tenantId, startDate, endDate };
    
        try {
            await axios.post(`${url}/bookings/create`, bookingData, {
                headers: { Authorization: `Bearer ${token}` }
            });

            navigate("/properties");
        } catch (error) {
            setError("Booking failed. Please try again.");
            console.error(error);
        }
    };

    return (
        <div className="container d-flex justify-content-center align-items-center min-vh-100">
            <div className="card shadow-lg p-4" style={{ maxWidth: "500px", width: "100%" }}>
                <h2 className="text-center mb-4 text-primary">Book a Property</h2>

                {error && <div className="alert alert-danger">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label className="form-label">Property ID:</label>
                        <input 
                            type="number" 
                            className="form-control" 
                            value={propertyId} 
                            onChange={(e) => setPropertyId(e.target.value)} 
                            required 
                        />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Start Date:</label>
                        <input 
                            type="date" 
                            className="form-control" 
                            value={startDate} 
                            onChange={(e) => setStartDate(e.target.value)} 
                            required 
                        />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">End Date:</label>
                        <input 
                            type="date" 
                            className="form-control" 
                            value={endDate} 
                            onChange={(e) => setEndDate(e.target.value)} 
                            required 
                        />
                    </div>

                    <button type="submit" className="btn btn-primary w-100">Book Now</button>
                </form>
            </div>
        </div>
    );
};

export default BookingForm;
