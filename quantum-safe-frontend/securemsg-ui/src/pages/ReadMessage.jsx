import { useEffect, useState, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";

export default function ReadMessage() {
    const { token } = useParams();
    const navigate = useNavigate();

    const [message, setMessage] = useState(null);
    const [error, setError] = useState(null);

    // 🔐 prevents double execution
    const hasFetched = useRef(false);

    useEffect(() => {
        if (hasFetched.current) return;
        hasFetched.current = true;

        fetch(`http://localhost:8080/api/messages/open/${token}`)
            .then(async (res) => {
                if (!res.ok) {
                    const text = await res.text();
                    throw new Error(text);
                }
                return res.json();
            })
            .then((data) => {
                setMessage(data.message);
            })
            .catch((err) => {
                setError(err.message);
            });
    }, [token]);

    return (
        <div className="min-h-screen bg-gray-900 flex items-center justify-center text-white">
            <div className="bg-gray-800 p-8 rounded-xl max-w-xl w-full">

                <h2 className="text-red-400 font-bold text-lg mb-2">
                    🔥 One-Time Secure Message
                </h2>

                <p className="text-gray-400 mb-4">
                    This message self-destructs after being read.
                </p>

                {message && (
                    <div className="bg-black p-4 rounded text-green-400 font-mono mb-4">
                        {message}
                    </div>
                )}

                {error && (
                    <p className="text-red-500 font-semibold mb-4">
                        {error}
                    </p>
                )}

                <div className="flex justify-between">
                    <button
                        className="bg-gray-700 hover:bg-gray-600 px-4 py-2 rounded"
                        onClick={() => navigate("/")}
                    >
                        Home
                    </button>

                    <button
                        className="bg-gray-700 hover:bg-gray-600 px-4 py-2 rounded"
                        onClick={() => navigate(-1)}
                    >
                        Back
                    </button>
                </div>
            </div>
        </div>
    );

}

