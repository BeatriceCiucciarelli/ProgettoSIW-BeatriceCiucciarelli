import axios from "axios";

// Istanza Axios unica: la baseURL si scrive in un posto solo,
// tutti i servizi la ereditano
const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

export default api;
