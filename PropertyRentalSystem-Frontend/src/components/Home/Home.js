import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import './Home.css'
import axios from "axios";
import { Search } from "lucide-react";

const Home = () => {
  const navigate = useNavigate();
  const [properties, setProperties] = useState([]);
  const [searchText,setSearchText] = useState();
  const url = `${process.env.REACT_APP_API_URL}`;
  console.log(url);
  const fetchProperties = async () => {
    try {
      const response = await axios.get(`${url}/properties`);
      setProperties(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const search = () => {
    navigate("/properties",{state:searchText})
  }


  useEffect(() => {
    fetchProperties();
  }, []);

  return (
    <div className="home-container">
      {/* Hero Section */}
      <section className="hero-section">
        <div className="hero-overlay">
          <div className="hero-content">
            <h1>Find Your Perfect Rental Property</h1>
            <p>Discover the best rental properties in your city with ease.</p>
            <div className="search-box">
              <input type="text"  value={searchText} onChange={(e)=>{setSearchText(e.target.value)}} placeholder="Search by City or Location" />
              <button onClick={search}>Search</button>
            </div>
          </div>
        </div>
      </section>

      {/* Explore Properties Section */}
      <section className="explore-properties">
        <h2>Explore Properties</h2>
        <div className="property-grid">
          {properties.slice(0, 4).map((item,index) => (  // Show only first 4 properties
            <div className="property-card" key={item.id}>
              {/* <img src={`${url}/images/${item.images[0].imageUrl}`} alt="Property" /> */}
              <img src={`/images/slide${index+1}.jpeg`} alt={`Property ${index + 1}`} />
              <div className="property-info">
                <h3>{item.title}</h3>
                <p><span className="location">{item.address}</span></p>
                <p className="price">{item.rent}/ month</p>
                <button onClick={() => navigate("/properties")}>View Details</button>
              </div>
            </div>
          ))}
        </div>
        <div className="view-more-container">
          <button className="view-more-btn" onClick={() => navigate("/properties")}>
            View More Properties
          </button>
        </div>
      </section>
    </div>
  );
};

export default Home;

