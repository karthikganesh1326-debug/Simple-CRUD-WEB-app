
import { useState } from "react";
import { Link } from "react-router-dom";
import { authAPI } from "./api";
import "./App.css";

function Login({ onLogin }) {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleLogin = async () => {
    try {
      const response = await authAPI.post("/login", {
        email,
        password
      });

      const token = response.data.token;

      localStorage.setItem("token", token);
      onLogin(token);

    } catch (error) {
      setMessage("Invalid email or password");
    }
  };

  return (
    <div className="auth-page">

      <div className="auth-brand">
        <span className="brand-icon">✓</span>
        <span>TaskFlow</span>
      </div>

      <div className="auth-card">

        <div className="auth-icon">↗</div>

        <h1>Welcome Back</h1>
        <p className="auth-subtitle">
          Login to continue managing your tasks.
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
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") handleLogin();
            }}
          />

          <button className="auth-btn" onClick={handleLogin}>
            Login <span>→</span>
          </button>

          {message && (
            <p className="auth-message">{message}</p>
          )}

        </div>

        <div className="auth-footer">
          Don't have an account?
          <Link to="/register"> Create Account</Link>
        </div>

      </div>

      <p className="auth-bottom-text">
        Stay focused. Get things done.
      </p>

    </div>
  );
}

export default Login;
