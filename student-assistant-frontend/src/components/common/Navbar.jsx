import { useContext } from "react";
import { AuthContext } from "../../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useContext(AuthContext);

  return (
    <div style={{ padding: "10px", background: "#eee" }}>
      <span>Student Assistant</span>

      {user && (
        <>
          <span style={{ marginLeft: "20px" }}>
            Logged in as: {user.username}
          </span>
          <button onClick={logout} style={{ marginLeft: "20px" }}>
            Logout
          </button>
        </>
      )}
    </div>
  );
};

export default Navbar;