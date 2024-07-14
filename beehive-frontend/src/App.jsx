import PropTypes from "prop-types";
import {
  Navigate,
  RouterProvider,
  createBrowserRouter,
} from "react-router-dom";
import { useGlobalAppContext } from "./context/app-context";
import { ErrorComponent } from "./ErrorComponent";
import { HomeComponent } from "./pages/home/home-component";
import { MyProfileComponent } from "./pages/my-profile/my-profile-component";
import { CreatePostComponent } from "./pages/post/create-post-component";
import { ReplyComponent } from "./pages/reply/reply-component";
import RootPostComponent from "./pages/root/root-post-component";
import RootPreComponent from "./pages/root/root-pre-component";

export function App() {
  const { isOnline } = useGlobalAppContext();

  const router = createBrowserRouter([
    {
      path: "/",
      element: isOnline ? <RootPostComponent /> : <RootPreComponent />,
      errorElement: <ErrorComponent />,
      children: isOnline
        ? [
            {
              index: true,
              element: <Navigate to="/home" replace={true} />,
            },
            {
              path: "/home",
              element: <HomeComponent />,
            },
            {
              path: "/:user/post/:postId",
              element: <ReplyComponent />,
            },
            {
              path: "/:user/posts",
              element: <MyProfileComponent />,
            },
            {
              path: "/create",
              element: <CreatePostComponent />,
            },
          ]
        : [],
    },
  ]);

  return <RouterProvider router={router} />;
}

App.propTypes = {
  props: PropTypes.object,
  global: PropTypes.object,
};
