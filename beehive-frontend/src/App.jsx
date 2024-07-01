/* eslint-disable react-hooks/exhaustive-deps */
import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import { useGlobalAppContext } from "./context/app-context";
import RootPostComponent from "./pages/root-post-component";
import RootPreComponent from "./pages/root-pre-component";

const App = () => {
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

  return <>{componentToLoad}</>;
};

App.propTypes = {
  props: PropTypes.object,
  global: PropTypes.object,
};

export default App;
