
import axios from "axios";

// Authentication API
export const authAPI = axios.create({
  baseURL: "http://localhost:8080/auth"
});

// Todo API
const API = axios.create({
  baseURL: "http://localhost:8080/todo"
});

// Automatically attach JWT to every Todo request
API.interceptors.request.use((config) => {

  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default API;
