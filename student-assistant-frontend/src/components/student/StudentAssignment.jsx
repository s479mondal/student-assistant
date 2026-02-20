import { useEffect, useState } from "react";
import { getAllAssignments } from "../../api/assignmentApi";

const StudentAssignments = () => {
  const [assignments, setAssignments] = useState([]);

  useEffect(() => {
    getAllAssignments().then((res) => {
      setAssignments(res.data);
    });
  }, []);

  return (
    <div>
      <h3>Assignments</h3>
      {assignments.map((a) => (
        <div key={a.id}>
          <b>{a.title}</b>
          <p>{a.description}</p>
        </div>
      ))}
    </div>
  );
};

export default StudentAssignments;