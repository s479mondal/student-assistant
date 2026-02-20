import axios from "./axiosConfig";

export const sendMessage = (data) => {
  return axios.post("/message/send", data);
};

export const getMessages = (userId) => {
  return axios.get(`/message/${userId}`);
};