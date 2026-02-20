import { useEffect, useState, useContext } from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { AuthContext } from "../context/AuthContext";

const Chat = () => {
  const { username } = useContext(AuthContext);
  const [stompClient, setStompClient] = useState(null);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const [receiver, setReceiver] = useState("");

  useEffect(() => {
    const socket = new SockJS("http://localhost:8081/ws");
    const client = Stomp.over(socket);

    client.connect({}, () => {
      client.subscribe(`/topic/messages/${username}`, (msg) => {
        setMessages((prev) => [...prev, JSON.parse(msg.body)]);
      });
    });

    setStompClient(client);
  }, [username]);

  const sendMessage = () => {
    stompClient.send("/app/chat.send", {}, JSON.stringify({
      senderUsername: username,
      receiverUsername: receiver,
      content: message
    }));
  };

  return (
    <div>
      <h3>Chat</h3>
      <input placeholder="Receiver username"
        onChange={(e) => setReceiver(e.target.value)} />
      <input placeholder="Message"
        onChange={(e) => setMessage(e.target.value)} />
      <button onClick={sendMessage}>Send</button>

      <ul>
        {messages.map((m, i) => (
          <li key={i}>{m.senderUsername}: {m.content}</li>
        ))}
      </ul>
    </div>
  );
};

export default Chat;