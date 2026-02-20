import { useState } from "react";
import { createSubject } from "../../api/adminApi";

function ManageSubjects() {
  const [name, setName] = useState("");

  const handleCreate = async () => {
    await createSubject({ name });
    alert("Subject Created");
    setName("");
  };

  return (
    <div>
      <h3>Create Subject</h3>

      <input
        type="text"
        placeholder="Subject Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <button onClick={handleCreate}>Create</button>
    </div>
  );
}

export default ManageSubjects;