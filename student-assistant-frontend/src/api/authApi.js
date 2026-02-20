import axios from "./axiosConfig";

export const loginUser = (data) => {
  return axios.post("/auth/login", data);
};

export const signupUser = (data) => {
  return axios.post("/auth/signup", data);
};