/* eslint-disable react-hooks/exhaustive-deps */
import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import { ErrorBoundary } from "react-error-boundary";
import { useGlobalAppContext } from "./context/app-context";
import { ErrorComponent } from "./ErrorComponent";
import RootPostComponent from "./pages/root/root-post-component";
import RootPreComponent from "./pages/root/root-pre-component";

export function App() {
  const { selectedPage } = useGlobalAppContext();

  const [componentToLoad, setComponentToLoad] = useState();

  useEffect(() => {
    if (localStorage.getItem("isAuthenticated")) {
      console.log("Authenticated!");
      setComponentToLoad(<RootPostComponent />);
    } else {
      console.log("Not Authenticated!");
      setComponentToLoad(<RootPreComponent />);
    }
  }, [selectedPage]);

  return (
    <ErrorBoundary FallbackComponent={ErrorComponent}>
      {componentToLoad}
    </ErrorBoundary>
  );
}

App.propTypes = {
  props: PropTypes.object,
  global: PropTypes.object,
};
