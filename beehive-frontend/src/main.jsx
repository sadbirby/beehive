import { Toaster } from "@/components/ui/sonner";
import React from "react";
import ReactDOM from "react-dom/client";
import "../styles/globals.css";
import App from "./App.jsx";
import { AppProvider } from "./context/app-context.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <main>
      <AppProvider>
        <App />
      </AppProvider>
    </main>
    <Toaster
      expand={true}
      richColors={true}
      pauseWhenPageIsHidden={true}
      toastOptions={{}}
    />
  </React.StrictMode>,
);
