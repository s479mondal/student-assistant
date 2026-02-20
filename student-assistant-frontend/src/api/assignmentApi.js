import axios from "./axiosConfig";

export const getAllAssignments = () => {
  return axios.get("/assignment/all");
};

export const submitAssignment = (data) => {
  return axios.post("/submission/submit", data);
};