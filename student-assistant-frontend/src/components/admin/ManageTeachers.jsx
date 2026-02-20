import { useEffect, useState } from "react";
import { createTeacher, getTeachers } from "../../api/adminApi";

function ManageTeachers() {
  const [teachers, setTeachers] = useState([]);
  const [form, setForm] = useState({
    username: "",
    password: "",
    email: "",
  });

  useEffect(() => {
    loadTeachers();
  }, []);

  const loadTeachers = async () => {
    const res = await getTeachers();
    setTeachers(res.data);
  };

  const handleCreate = async () => {
    await createTeacher(form);
    alert("Teacher Created");
    loadTeachers();
  };

  return (
    <div>
      <h3>Create Teacher</h3>

      <input
        placeholder="Username"
        onChange={(e) =>
          setForm({ ...form, username: e.target.value })
        }
      />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) =>
          setForm({ ...form, password: e.target.value })
        }
      />

      <input
        placeholder="Email"
        onChange={(e) =>
          setForm({ ...form, email: e.target.value })
        }
      />

      <button onClick={handleCreate}>Create</button>

      <h3>All Teachers</h3>

      <ul>
        {teachers.map((t) => (
          <li key={t.id}>{t.username}</li>
        ))}
      </ul>
    </div>
  );
}

export default ManageTeachers;