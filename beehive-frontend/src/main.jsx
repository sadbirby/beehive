import { Toaster } from "@/components/ui";
import React from "react";
import ReactDOM from "react-dom/client";
import "../styles/globals.css";
import App from "./App";
import { AppProvider } from "./context/app-context.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <AppProvider>
      <App />
      <Toaster
        expand={true}
        richColors={true}
        pauseWhenPageIsHidden={true}
        position="bottom-right"
        toastOptions={{
          classNames: {
            toast: "!rounded-md",
          },
        }}
      />
    </AppProvider>
  </React.StrictMode>,
);
