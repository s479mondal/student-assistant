import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

const StudentDashboard = () => {
  const { user } = useContext(AuthContext);

  return (
    <div>
      <h2>Student Dashboard</h2>
      <p>Welcome {user.username}</p>
    </div>
  );
};

export default StudentDashboard;