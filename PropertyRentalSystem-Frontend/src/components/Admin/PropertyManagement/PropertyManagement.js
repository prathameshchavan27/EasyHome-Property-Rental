import React, { useState, useEffect } from "react";
import AddProperty from "../../AddProperty/AddProperty";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function PropertyManagement() {
  const [properties, setProperties] = useState([]);
  const navigate = useNavigate();
  const token = localStorage.getItem("jwtToken");
  // Fetch properties from the API
  useEffect(() => {
   fetchProperty();
  }, []);

  const fetchProperty = async() => {
    try{
      const response = await axios.get("http://localhost:8081/properties");
      setProperties(response.data);
    }catch(error){
      console.log(error);
    }
  }

  const AddProperty = () => {
    navigate("/add-property");
  }

  const deleteProperty = async(id) => {
    try{
      const response = await axios.delete(`http://localhost:8081/landlord/properties/${id}`,{headers:{
        Authorization: `Bearer ${token}`
      }});
      console.log(response.data);
      fetchProperty();
    }catch(error){
      console.log(error);
    }
  }

  const updateProperty = (property) => {
    navigate('/update-property',{state:property});
  }

  return (
    <div>
      <h3>Property Management</h3>
      <button onClick={AddProperty} className="btn btn-primary mb-3">Add Property</button>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Property Name</th>
            <th>Owner</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {properties.map(property => (
            <tr key={property.id}>
              <td>{property.title}</td>
              <td>{property.owner.name}</td>
              <td>{property.available===true?"Available":"Booked"}</td>
              <td>
              {property.available===true?(<>
                <button onClick={()=>updateProperty(property)} className="btn btn-success btn-sm">Update</button>
                <button onClick={()=>deleteProperty(property.id)} className="btn btn-danger btn-sm">Delete</button>
              </>
                ):null}
                {/* <button className="btn btn-info btn-sm">Edit</button> */}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PropertyManagement;
