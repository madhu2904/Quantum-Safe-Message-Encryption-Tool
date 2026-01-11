const BASE_URL = "http://localhost:8080/api/messages";

export async function fetchInbox(userId) {
    const res = await fetch(`${BASE_URL}/inbox/${userId}`);

    if (!res.ok) {
        throw new Error("Failed to load inbox");
    }

    return res.json();
}

export async function sendMessage(payload) {
    const res = await fetch(`${BASE_URL}/send`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

    if (!res.ok) {
        throw new Error("Send failed");
    }

    return res.json();
}

export async function openMessage(token) {
    const res = await fetch(`${BASE_URL}/open/${token}`);

    if (!res.ok) {
        const text = await res.text();
        throw new Error(text);
    }

    return res.json();
}
