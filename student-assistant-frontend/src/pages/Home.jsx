import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div style={{ textAlign: "center", marginTop: "100px" }}>
      <h1>Student Assistant</h1>

      <Link to="/login">
        <button>Login</button>
      </Link>

      <Link to="/signup">
        <button style={{ marginLeft: "20px" }}>Signup</button>
      </Link>
    </div>
  );
};

export default Home;