import React, { createContext, useContext, useState } from "react";

// Create Context for Auth
const AuthContext = createContext();

// Create a custom hook to use the AuthContext
export const useAuth = () => useContext(AuthContext);

// Create a provider component to wrap your app
export const AuthProvider = ({ children }) => {
  const [isLogin, setIsLogin] = useState(false); // Initial state is false (not logged in)
const [role, setRole] = useState();
const [propertiesg, setPropertiesg] = useState([]);
  return (
    <AuthContext.Provider value={{ isLogin, setIsLogin,role,setRole,propertiesg, setPropertiesg}}>
      {children}
    </AuthContext.Provider>
  );
};
