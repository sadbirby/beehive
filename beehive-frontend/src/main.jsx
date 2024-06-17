import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import '../styles/globals.css'
import { AppProvider } from './context/app-context.jsx'
import { Toaster } from '@/components/ui/toaster.jsx'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <main>
      <AppProvider>
        <App />
      </AppProvider>
    </main>
    <Toaster />
  </React.StrictMode>
)
