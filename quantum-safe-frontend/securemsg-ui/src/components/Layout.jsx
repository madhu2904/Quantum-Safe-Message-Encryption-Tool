import { Link, Outlet, useParams } from "react-router-dom";

export default function Layout() {
    const { userId } = useParams();

    return (
        <div className="min-h-screen bg-gray-100">
            <nav className="bg-gray-900 text-white px-6 py-4 flex justify-between">
                <span className="font-bold text-lg">🔐 SecureMsg</span>

                {userId && (
                    <div className="flex gap-4">
                        <Link to={`/inbox/${userId}`} className="hover:text-green-400">
                            Inbox
                        </Link>
                        <Link to={`/compose/${userId}`} className="hover:text-blue-400">
                            Compose
                        </Link>
                        <Link to={`/alerts/${userId}`} className="hover:text-red-400">
                            Alerts
                        </Link>
                    </div>
                )}
            </nav>

            <main className="p-6">
                <Outlet />
            </main>
        </div>
    );
}
