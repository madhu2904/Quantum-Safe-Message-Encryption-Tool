import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function Alerts() {
    const { userId } = useParams();
    const [alerts, setAlerts] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8080/api/messages/alerts/${userId}`)
            .then(res => res.json())
            .then(setAlerts);
    }, [userId]);

    return (
        <div className="p-6">
            <h2 className="text-xl font-bold mb-4 text-red-600">
                🚨 Security Alerts
            </h2>

            {alerts.length === 0 && (
                <p>No security incidents detected.</p>
            )}

            {alerts.map(a => (
                <div key={a.messageId} className="border-l-4 border-red-600 p-3 mb-3 bg-red-50">
                    <p><strong>Message:</strong> {a.messageId}</p>
                    <p><strong>Receiver:</strong> {a.receiverId}</p>
                    <p><strong>Attempts:</strong> {a.accessAttempts}</p>
                </div>
            ))}
        </div>
    );
}
