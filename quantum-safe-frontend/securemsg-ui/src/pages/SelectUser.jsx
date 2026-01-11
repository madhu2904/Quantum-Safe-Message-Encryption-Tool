import { useNavigate } from "react-router-dom";

export default function SelectUser() {
    const navigate = useNavigate();

    function selectUser(userId) {
        navigate(`/inbox/${userId}`);
    }

    return (
        <div className="min-h-screen bg-gray-900 flex items-center justify-center">
            <div className="bg-gray-800 p-8 rounded-xl shadow-xl w-80">
                <h2 className="text-2xl font-bold text-white text-center mb-6">
                    🔐 Secure Messaging
                </h2>

                <button
                    className="w-full mb-4 bg-blue-600 hover:bg-blue-700 text-white py-2 rounded"
                    onClick={() => selectUser("alice")}
                >
                    Login as Alice
                </button>

                <button
                    className="w-full bg-green-600 hover:bg-green-700 text-white py-2 rounded"
                    onClick={() => selectUser("bob")}
                >
                    Login as Bob
                </button>
            </div>
        </div>
    );

}
