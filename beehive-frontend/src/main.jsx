import { Toaster } from "@/components/ui";
import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "../styles/globals.css";
import { App } from "./App.jsx";
import { AppProvider } from "./context/app-context.jsx";
import { ErrorComponent } from "./ErrorComponent";
import { HomeComponent } from "./pages/home/home-component";
import { MyProfileComponent } from "./pages/my-profile/my-profile-component";
import { ReplyComponent } from "./pages/reply/reply-component";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorComponent />,
    children: [
      {
        path: "/home",
        element: <HomeComponent />,
      },
      {
        path: "/post/:postId",
        element: <ReplyComponent />,
      },
      {
        path: "/:user/posts",
        element: <MyProfileComponent />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <AppProvider>
      <RouterProvider router={router} />
      <Toaster
        expand={true}
        richColors={true}
        pauseWhenPageIsHidden={true}
        position="top-center"
        toastOptions={{
          classNames: {
            toast: "!rounded-full !h-2",
          },
        }}
      />
    </AppProvider>
  </React.StrictMode>,
);
