import { Suspense, lazy } from "react";
import {
  Navigate,
  RouterProvider,
  createBrowserRouter,
} from "react-router-dom";
import { LoadingSpinner } from "./components/ui";
import { useGlobalAppContext } from "./context/app-context";

// Lazy imports
const ErrorComponent = lazy(() => import("./pages/error/ErrorComponent"));
const HomeComponent = lazy(() => import("./pages/home/home-component"));
const MyProfileComponent = lazy(
  () => import("./pages/my-profile/my-profile-component"),
);
const CreatePostComponent = lazy(
  () => import("./pages/post/create-post-component"),
);
const ReplyComponent = lazy(() => import("./pages/reply/reply-component"));
const RootPostComponent = lazy(
  () => import("./pages/root/root-post-component"),
);
const RootPreComponent = lazy(() => import("./pages/root/root-pre-component"));

const App = () => {
  const { isOnline } = useGlobalAppContext();

  // Loading component
  const Loading = () => (
    <div className="flex flex-row min-h-screen justify-center items-center">
      <LoadingSpinner />
    </div>
  );

  const router = createBrowserRouter([
    {
      path: "/",
      element: (
        <Suspense fallback={<Loading />}>
          {isOnline ? <RootPostComponent /> : <RootPreComponent />}
        </Suspense>
      ),
      errorElement: (
        <Suspense fallback={<Loading />}>
          <ErrorComponent />
        </Suspense>
      ),
      children: isOnline
        ? [
            {
              index: true,
              element: <Navigate to="/home" replace={true} />,
            },
            {
              path: "/home",
              element: (
                <Suspense fallback={<Loading />}>
                  <HomeComponent />
                </Suspense>
              ),
            },
            {
              path: "/:user/post/:postId",
              element: (
                <Suspense fallback={<Loading />}>
                  <ReplyComponent />
                </Suspense>
              ),
            },
            {
              path: "/:user/posts",
              element: (
                <Suspense fallback={<Loading />}>
                  <MyProfileComponent />
                </Suspense>
              ),
            },
            {
              path: "/create",
              element: (
                <Suspense fallback={<Loading />}>
                  <CreatePostComponent />
                </Suspense>
              ),
            },
          ]
        : [],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default App;
