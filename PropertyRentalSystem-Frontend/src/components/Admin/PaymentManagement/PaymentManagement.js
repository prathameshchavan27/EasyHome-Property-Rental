import axios from "axios";
import React, { useState, useEffect } from "react";

function PaymentManagement() {
  const [payments, setPayments] = useState([]);
    const token = localStorage.getItem('jwtToken');
  // Fetch payments from the API
  const url = `${process.env.REACT_APP_API_URL}`;
  const fetchPayments = async () => {
    try{
        const response = await axios.get(`${url}/payments/getpayments`,{headers:{
            Authorization: `Bearer ${token}`
        }});
        console.log(response.data)
        setPayments(response.data);
    }catch(error){
        console.log(error);
    }   
    
  }
  useEffect(() => {
    // fetch("http://localhost:8081/payments")
    //   .then(response => response.json())
    //   .then(data => {console.log(data);setPayments(data)})
    //   .catch(error => console.error("Error fetching payments:", error));
    fetchPayments();
  }, []);

  return (
    <div>
      <h3>Payment Management</h3>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Booking</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {payments.map(payment => (
            <tr key={payment.id}>
              <td>{payment.bookingId}</td>
              <td>{payment.amount}</td>
              <td>{payment.paymentStatus}</td>
              <td>
              {payment.paymentStatus!=='COMPLETED'?(<>
              
                <button className="btn btn-success btn-sm">Mark as Completed</button>
                <button className="btn btn-danger btn-sm">Mark as Failed</button>
                </>
                ):null}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PaymentManagement;
