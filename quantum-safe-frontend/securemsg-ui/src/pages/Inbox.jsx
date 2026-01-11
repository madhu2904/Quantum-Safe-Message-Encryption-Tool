import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

function statusBadge(status) {
    switch (status) {
        case "UNREAD":
            return <span className="text-green-400">🟢 Unread</span>;
        case "BURNED":
            return <span className="text-red-400">🔥 Burned</span>;
        case "REPLAY_DETECTED":
            return <span className="text-orange-400">⚠ Replay Detected</span>;
        case "EXPIRED":
            return <span className="text-gray-400">⏰ Expired</span>;
        default:
            return null;
    }
}


export default function Inbox() {
    const { userId } = useParams();
    const navigate = useNavigate();

    const [messages, setMessages] = useState([]);
    const [alerts, setAlerts] = useState([]);

    // 📥 Fetch inbox messages
    useEffect(() => {
        fetch(`http://localhost:8080/api/messages/inbox/${userId}`)
            .then(res => res.json())
            .then(setMessages)
            .catch(console.error);
    }, [userId]);

    // 🚨 Fetch sender alerts
    useEffect(() => {
        fetch(`http://localhost:8080/api/messages/alerts/${userId}`)
            .then(res => res.json())
            .then(setAlerts)
            .catch(console.error);
    }, [userId]);

    return (
        <div className="min-h-screen bg-gray-900 text-white p-6">
            <div className="max-w-3xl mx-auto">

                {/* Header */}
                <div className="flex justify-between items-center mb-6">
                    <h2 className="text-xl font-bold">📥 Inbox — {userId}</h2>
                    <button
                        className="bg-gray-700 hover:bg-gray-600 px-4 py-1 rounded"
                        onClick={() => navigate("/")}
                    >
                        Home
                    </button>
                </div>

                {/* 🚨 Sender Alerts */}
                {alerts.length > 0 && (
                    <div className="bg-red-700 p-4 rounded mb-6">
                        <h3 className="font-bold mb-2">🚨 Security Alerts</h3>
                        <ul className="list-disc list-inside text-sm">
                            {alerts.map((a, i) => (
                                <li key={i}>
                                    Replay attempt on message {a.messageId}
                                </li>
                            ))}
                        </ul>
                    </div>
                )}

                {/* Crypto Banner */}
                <div className="bg-green-700 text-white p-4 rounded mb-6 text-center font-semibold">
                    🔐 Quantum-Safe Encryption Active
                </div>

                <button
                    className="mb-6 bg-blue-600 hover:bg-blue-700 px-4 py-2 rounded"
                    onClick={() => navigate(`/compose/${userId}`)}
                >
                    ✍️ Compose Secure Message
                </button>

                {/* Inbox */}
                {messages.length === 0 && (
                    <p className="text-gray-400 text-center">No messages</p>
                )}

                <ul className="space-y-4">
                    {messages.map(m => (
                        <li
                            key={m.token}
                            className="bg-gray-800 p-4 rounded flex justify-between items-center"
                        >
                            <div>
                                <p className="font-semibold">
                                    From: <span className="text-blue-400">{m.senderId}</span>
                                </p>
                                <div className="text-sm mt-1">
                                    {statusBadge(m.status)}
                                </div>
                            </div>

                            <button
                                className="bg-blue-600 hover:bg-blue-700 px-4 py-1 rounded"
                                onClick={() => navigate(`/read/${m.token}`)}
                            >
                                Open
                            </button>

                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}
