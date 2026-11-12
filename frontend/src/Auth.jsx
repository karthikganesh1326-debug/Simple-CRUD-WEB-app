
import { useState } from "react";
import { authAPI } from "./api";

function Auth({ onLogin }) {

  const [isLogin, setIsLogin] = useState(true);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async () => {
    try {
      if (isLogin) {
        // LOGIN
        const response = await authAPI.post("/login", {
          email,
          password
        });

        const token = response.data.token;

        localStorage.setItem("token", token);

        onLogin(token);

      } else {
        // REGISTER
        await authAPI.post("/register", {
          email,
          password
        });

        setMessage("Registration successful! Please login.");
        setIsLogin(true);
      }

    } catch (error) {
      setMessage(
        error.response?.data?.message ||
        error.response?.data ||
        "Something went wrong"
      );
    }
  };

  return (
    <div className="app">
      <div className="container">

        <div className="form-card">
          <h1>{isLogin ? "Welcome Back" : "Create Account"}</h1>

          <p>
            {isLogin
              ? "Login to access your Todo Dashboard"
              : "Register to get started"}
          </p>

          <br />

          <input
            type="email"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            type="password"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button className="add-btn" onClick={handleSubmit}>
            {isLogin ? "Login" : "Register"}
          </button>

          <p>{message}</p>

          <p style={{ marginTop: "20px" }}>
            {isLogin
              ? "Don't have an account?"
              : "Already have an account?"}

            <button
              onClick={() => {
                setIsLogin(!isLogin);
                setMessage("");
              }}
            >
              {isLogin ? " Register" : " Login"}
            </button>
          </p>

        </div>

      </div>
    </div>
  );
}

export default Auth;
