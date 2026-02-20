import { useState } from "react";
import ManageStudents from "../components/admin/ManageStudents";
import ManageSubjects from "../components/admin/ManageSubjects";

function AdminDashboard() {
  const [activeTab, setActiveTab] = useState("students");

  return (
    <div>
      <h2>Admin Dashboard</h2>

      <button onClick={() => setActiveTab("students")}>
        Manage Students
      </button>

      <button onClick={() => setActiveTab("subjects")}>
        Manage Subjects
      </button>

      <button onClick={() => setActiveTab("teachers")}>
        Manage Teachers
      </button>

      {activeTab === "students" && <ManageStudents />}
      {activeTab === "subjects" && <ManageSubjects />}
      {activeTab === "teachers" && <ManageTeachers />}
    </div>
  );
}

export default AdminDashboard;