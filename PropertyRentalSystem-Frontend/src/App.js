import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  useNavigate,
} from "react-router-dom";
import "./App.css";
import AddProperty from "./components/AddProperty/AddProperty";
import PropertyDetails from "./components/PropertyDetails/PropertyDetails";
import Login from "./components/Login/LoginPage";
import Register from "./components/Register/Register";
import PropertiesList from "./components/PropertyList/Properties";
import Home from "./components/Home/Home";
import Navbar from "./components/Navbar/Navbar";
import { AuthProvider } from "./Auth/AuthContext";
import UpdateProperty from "./components/UpdateProperty/UpdateProperty";
import ManageProperty from "./components/PropertyList/ManageProperty";
import BookingForm from "./components/BookingForm/BookingForm";
import ManageBooking from "./components/ManageBooking/ManageBooking";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import BookingRequests from "./components/ManageBooking/BookingRequests";
import StripePayment from "./components/PaymentCheckout/CheckoutForm";
import ResetPassword from "./components/PasswordManager/ResetPassword";
import ForgotPassword from "./components/PasswordManager/ForgotPassword";
import UserManagement from "./components/Admin/UserManagement/UserManagement";
import BookingManagement from "./components/Admin/BookingManagement/BookingManagement";
import Layout from "./components/Admin/Layout";
import PropertyManagement from "./components/Admin/PropertyManagement/PropertyManagement";
import PaymentManagement from "./components/Admin/PaymentManagement/PaymentManagement";
import FeedbackReports from "./components/Admin/Feedbacks/Feedbacks";
import ProfilePage from "./components/Profile/ProfilePage";
import ForbiddenPage from "./Auth/Forbidden";
import ProtectedRoute from "./Auth/ProtectedRoute";

function App() {
  return (
    <>
      <AuthProvider>
        <Router>
          <Navbar />
          <div className="main-content">
            <Routes>
              <Route path="/home" element={<Home />} />
              <Route path="/" element={<Register />} />
              <Route path="/login" element={<Login />} />
              <Route path="/forgot-password" element={<ForgotPassword />} />
              <Route path="/reset-password" element={<ResetPassword />} />
              <Route path="/add-property" element={<AddProperty />} />
              <Route
                path="/property/:propertyId"
                element={<PropertyDetails />}
              />
              <Route path="/properties" element={<PropertiesList />} />
              <Route path="/manage-properties" element={<ManageBooking />} />
              <Route path="/update-property" element={<UpdateProperty />} />
              <Route path="/book-property" element={<BookingForm />} />
              <Route path="/requests" element={<BookingRequests />} />
              <Route path="/payment" element={<StripePayment />} />
              <Route path="/profile" element={<ProfilePage />} />
              <Route path="/forbidden" element={<ForbiddenPage/>}/>

              <Route element={<ProtectedRoute allowedRole="ADMIN" />}>
                <Route path="/admin" element={<Layout />}>
                  <Route path="user-management" element={<UserManagement />} />
                  <Route
                    path="booking-management"
                    element={<BookingManagement />}
                  />
                  <Route
                    path="property-management"
                    element={<PropertyManagement />}
                  />
                  <Route
                    path="payment-management"
                    element={<PaymentManagement />}
                  />
                  <Route path="feedback-reports" element={<FeedbackReports />} />
                </Route>
              </Route>
            </Routes>
          </div>
        </Router>
      </AuthProvider>
    </>
  );
}

export default App;
