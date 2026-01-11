import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

export default function Compose() {
    const { userId } = useParams();
    const navigate = useNavigate();

    const [receiver, setReceiver] = useState("");
    const [message, setMessage] = useState("");

    /*async function send() {
        await fetch("http://localhost:8080/api/messages/send", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                senderId: userId,
                receiverId: receiver,
                message,
                expiryMinutes: 10
            })
        });

        navigate(`/inbox/${userId}`);
    }*/

    async function send() {
        try {
            const res = await fetch("http://localhost:8080/api/messages/send", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    senderId: userId,
                    receiverId: receiver,
                    message,
                    expiryMinutes: 10
                })
            });

            if (!res.ok) {
                const text = await res.text();
                throw new Error(text);
            }

            navigate(`/inbox/${userId}`);

        } catch (err) {
            alert("Send failed: " + err.message);
            console.error(err);
        }
    }



    return (
        <div className="min-h-screen bg-gray-900 text-white p-6">
            <div className="max-w-xl mx-auto bg-gray-800 p-6 rounded-xl">

                <div className="flex justify-between mb-4">
                    <h2 className="text-xl font-bold">✉️ Compose as {userId}</h2>
                    <button
                        className="bg-gray-700 px-3 py-1 rounded"
                        onClick={() => navigate("/")}
                    >
                        Home
                    </button>
                </div>

                <input
                    className="w-full mb-4 p-3 rounded bg-gray-700 outline-none"
                    placeholder="Receiver ID"
                    value={receiver}
                    onChange={e => setReceiver(e.target.value)}
                />

                <textarea
                    className="w-full h-32 mb-4 p-3 rounded bg-gray-700 outline-none"
                    placeholder="Confidential message"
                    value={message}
                    onChange={e => setMessage(e.target.value)}
                />

                <div className="flex gap-4">
                    <button
                        onClick={send}
                        style={{ pointerEvents: "auto", position: "relative" }}
                        className="bg-green-600 hover:bg-green-700 px-4 py-2 rounded"
                    >
                        Encrypt & Send
                    </button>



                    <button
                        className="bg-gray-600 hover:bg-gray-500 px-4 py-2 rounded"
                        onClick={() => navigate(`/inbox/${userId}`)}
                    >
                        Back
                    </button>
                </div>
            </div>
        </div>
    );

}
