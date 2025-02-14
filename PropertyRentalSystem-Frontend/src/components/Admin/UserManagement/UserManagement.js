
import axios from "axios";
import React, { useState, useEffect } from "react";
import "./UserManagement.css"; // Custom CSS for user management styling

function UserManagement() {
  const [users, setUsers] = useState([]);
  const token = localStorage.getItem("jwtToken");
  const url = `${process.env.REACT_APP_API_URL}`;
  const fetchUsers = async () => {
    try {
      const response = await axios.get(`${url}/admin/users`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const deleteUser = async (id) => {
    try {
      await axios.delete(`http://localhost:8081/admin/users/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Update the user list after deletion
      setUsers(users.filter((user) => user.id !== id));
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  useEffect(() => {
    fetchUsers();
    // eslint-disable-next-line
  }, []);

  return (
    <div className="user-management card p-4">
      <h3 className="mb-4">User Management</h3>
      {/* <button className="btn btn-primary">Add New Admin</button> */}
      <table className="table table-hover table-bordered">
        <thead className="table-light">
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th style={{ width: "150px" }}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>{user.role}</td>
              <td>
                <button className="btn btn-warning btn-sm me-2">Edit</button>
                {user.role === "ADMIN" ? null : (
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => deleteUser(user.id)}
                  >
                    Delete
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UserManagement;
