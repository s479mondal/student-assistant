import { useState } from "react";
import { sendMessage } from "../../api/messageApi";

const ChatWindow = () => {
  const [message, setMessage] = useState("");

  const handleSend = () => {
    sendMessage({
      senderId: 1,
      receiverId: 2,
      content: message,
    });
  };

  return (
    <div>
      <h3>Chat</h3>
      <input
        value={message}
        onChange={(e) => setMessage(e.target.value)}
      />
      <button onClick={handleSend}>Send</button>
    </div>
  );
};

export default ChatWindow;