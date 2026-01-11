import { useState } from 'react'
import { openMessage } from '../services/api'

export default function OpenMessage() {
    const [token, setToken] = useState('')
    const [result, setResult] = useState(null)

    async function handleOpen() {
        const res = await openMessage(token)
        setResult(res)
    }

    return (
        <div>
            <h3>🔓 Open Secure Message</h3>

            <input value={token} onChange={e => setToken(e.target.value)} placeholder="Paste token here" />
            <br /><br />

            <button onClick={handleOpen}>Open</button>

            {result && (
                <pre style={{ marginTop: '10px', background: '#eee', padding: '10px' }}>
{JSON.stringify(result, null, 2)}
        </pre>
            )}
        </div>
    )
}
