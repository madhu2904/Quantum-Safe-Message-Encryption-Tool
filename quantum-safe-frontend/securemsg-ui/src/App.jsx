import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import "./index.css";
import SelectUser from "./pages/SelectUser";
import Inbox from "./pages/Inbox";
import Compose from "./pages/Compose";
import ReadMessage from "./pages/ReadMessage";

function App() {
    return (
        <BrowserRouter>
            <Routes>

                {/* User selection */}
                <Route path="/" element={<SelectUser />} />

                {/* Inbox */}
                <Route path="/inbox/:userId" element={<Inbox />} />

                {/* Compose message */}
                <Route path="/compose/:userId" element={<Compose />} />

                {/* Read message (token-based) */}
                <Route path="/read/:token" element={<ReadMessage />} />

                {/* Fallback */}
                <Route path="*" element={<Navigate to="/" />} />

            </Routes>
        </BrowserRouter>
    );
}

export default App;
