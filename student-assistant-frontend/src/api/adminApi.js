import axios from "./axiosConfig";

export const createTeacher = (data) =>
  axios.post("/admin/create-teacher", data);

export const createSubject = (data) =>
  axios.post("/admin/create-subject", data);

export const assignTeacher = (teacherId, subjectId) =>
  axios.post(`/admin/assign?teacherId=${teacherId}&subjectId=${subjectId}`);

export const getTeachers = () =>
  axios.get("/admin/teachers");

export const createStudent = (data) =>
  axios.post("/admin/create-student", data);

export const getStudents = (department) =>
  axios.get("/admin/students", {
    params: department ? { department } : {},
  });