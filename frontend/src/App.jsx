
import { useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./Login";
import Register from "./Register";
import TodoPage from "./TodoPage";

function App() {

  const [token, setToken] = useState(
    localStorage.getItem("token")
  );

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken(null);
  };

  return (
    <Routes>

      {/* Login Page */}
      <Route
        path="/"
        element={
          token
            ? <Navigate to="/todo" />
            : <Login onLogin={setToken} />
        }
      />

      {/* Register Page */}
        <Route
          path="/register"
          element={
            token ? <Navigate to="/todo" /> : <Register onLogin={setToken} />
          }
        />

      {/* Todo Page */}
      <Route
        path="/todo"
        element={
          token
            ? <TodoPage onLogout={handleLogout} />
            : <Navigate to="/" />
        }
      />

    </Routes>
  );
}

export default App;
