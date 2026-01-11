import { useState } from 'react'
import { sendMessage } from '../services/api'

export default function SendMessage() {
    const [senderId, setSender] = useState('alice')
    const [receiverId, setReceiver] = useState('bob')
    const [message, setMessage] = useState('')
    const [result, setResult] = useState(null)

    async function handleSend() {
        const res = await sendMessage({
            senderId,
            receiverId,
            message,
            expiryMinutes: 10
        })
        setResult(res)
    }

    return (
        <div>
            <h3>✉️ Send Secure Message</h3>

            <input value={senderId} onChange={e => setSender(e.target.value)} placeholder="Sender" />
            <br /><br />
            <input value={receiverId} onChange={e => setReceiver(e.target.value)} placeholder="Receiver" />
            <br /><br />
            <textarea value={message} onChange={e => setMessage(e.target.value)} placeholder="Secret message" />
            <br /><br />

            <button onClick={handleSend}>Encrypt & Send</button>

            {result && (
                <pre style={{ marginTop: '10px', background: '#eee', padding: '10px' }}>
{JSON.stringify(result, null, 2)}
        </pre>
            )}
        </div>
    )
}
