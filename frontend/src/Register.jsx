
import { Link } from "react-router-dom";
import { useState } from "react";
import { authAPI } from "./api";
import "./App.css";

function Register({ onLogin }) {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleRegister = async () => {
    try {
      // Step 1: Register user
      await authAPI.post("/register", {
        email,
        password
      });

      // Step 2: Automatically login
      const response = await authAPI.post("/login", {
        email,
        password
      });

      // Step 3: Get JWT token
      const token = response.data.token;

      // Step 4: Save token
      localStorage.setItem("token", token);

      // Step 5: Update App state and navigate to Todo
      onLogin(token);

    } catch (error) {
      setMessage(
        error.response?.data?.message || "Registration failed"
      );
    }
  };

  return (
    <div className="auth-page">

      <div className="auth-brand">
        <span className="brand-icon">✓</span>
        <span>TaskFlow</span>
      </div>

      <div className="auth-card">

        <div className="auth-icon">✦</div>

        <h1>Create Account</h1>
        <p className="auth-subtitle">
          Start organizing your tasks today.
        </p>

        <div className="auth-form">

          <label>Email Address</label>
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <label>Password</label>
          <input
            type="password"
            placeholder="Create a password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button className="auth-btn" onClick={handleRegister}>
            Create Account <span>→</span>
          </button>

          {message && (
            <p className="auth-message">{message}</p>
          )}

        </div>

        <div className="auth-footer">
          Already have an account?
          <Link to="/"> Login</Link>
        </div>

      </div>

      <p className="auth-bottom-text">
        A little progress every day.
      </p>

    </div>
  );
}

export default Register;
