# 🏡 Property Rental System

![Screenshot 2025-02-21 at 6 16 48 AM](https://github.com/user-attachments/assets/958c6f1a-6ea3-4a8a-9e61-57451a4f3f24)

## https://easy-home-property-rental.vercel.app/home

## 📌 Overview
The **Property Rental System** is a full-stack web application designed to streamline property rentals. It allows landlords to list properties, manage bookings, and handle payments, while tenants can search, book, and review properties. The system supports **role-based access control** for Landlords, Tenants, and Admins.

## 🚀 Features
✅ **User Authentication (JWT)** – Secure login/signup with role-based access (Tenant, Landlord, Admin).  
✅ **Property Management** – Landlords can add, update, and delete properties.  
✅ **Search & Filter** – Tenants can search properties based on location, price, and amenities.  
✅ **Bookings & Payments** – Tenants can book properties and pay online via **Stripe**.  
✅ **Reviews & Ratings** – Tenants can leave feedback on properties.  
✅ **Admin Panel** – Manage users, properties, and transactions.  

## 🏗️ Tech Stack
### 🔹 Backend:
- **Spring Boot** (REST APIs, JWT Security)
- **Spring Security** (Authentication & Authorization)
- **Hibernate + MySQL** (Database Management)
- **Docker** (Containerized Deployment)

### 🔹 Frontend:
- **React.js** (User Interface)
- **Redux** (State Management)
- **Bootstrap CSS** (Styling)
- **Axios** (API Requests)

### 🔹 Other Tools:
- **Stripe API** (Payment Gateway)
- **Swagger** (API Documentation)
- **Docker** (Deployment)

## 🎯 Database Schema
```
Users (id, name, email, password, role)
Properties (id, title, description, address, rent, landlord_id)
Bookings (id, tenant_id, property_id, start_date, end_date, payment_status)
Reviews (id, user_id, property_id, rating, comments, created_at)
Payments (id, booking_id, amount, status, created_at)
```

## 📷 Screenshots
| Homepage | Properties Page | Property Details | 
|---|---|---|
| ![Screenshot 2025-02-21 at 6 16 48 AM](https://github.com/user-attachments/assets/958c6f1a-6ea3-4a8a-9e61-57451a4f3f24)
  | ![Uploading Screenshot 2025-02-10 at 4.02.04 PM.png…]()
 | ![Uploading Screenshot 2025-01-29 at 11.38.34 AM.png…]() |


## 🛠️ Installation & Setup
### 🔧 Prerequisites
- Install **Java 17+, Node.js, MySQL, Docker**

### 🔹 Backend Setup
```sh
# Clone the repository
git clone https://github.com/your-username/property-rental-system.git
cd property-rental-system/backend

# Build and Run Spring Boot App
docker-compose up -d
```

### 🔹 Frontend Setup
```sh
cd ../frontend
npm install
npm start
```

## 🌍 Deployment


## 📜 API Documentation
API Docs are available via **Swagger UI**:
```
http://localhost:8081/swagger-ui/index.html
```

## 👨‍💻 Contributors
🚀 Built by **[Prathamesh Chavan](https://github.com/prathameshchavan27)**

## 📄 License
This project is licensed under the **MIT License**.
