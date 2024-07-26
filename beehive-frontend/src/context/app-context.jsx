import PropTypes from "prop-types";
import { createContext, useContext, useReducer } from "react";
import { globalActionTypes } from "./action-types";
import { reducer } from "./app-reducer";

const initialState = {
  colorTheme: localStorage.getItem("color-theme") || "dark",
  isOnline: localStorage.getItem("isAuthenticated") || false,
  loaderEnabled: true,
  userData: {},
  postData: [],
  selectedPost: {},
  selectedSortingMethod: "Newest",
};

const AppContext = createContext();

const AppProvider = ({ children, ...props }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  const showLoader = () => {
    return dispatch({
      type: globalActionTypes.SHOW_LOADER,
    });
  };

  const hideLoader = () => {
    return dispatch({
      type: globalActionTypes.HIDE_LOADER,
    });
  };

  const updateSelectedSortingMethod = (selectedSortingMethod) => {
    return dispatch({
      type: globalActionTypes.UPDATE_SELECTED_SORTING_METHOD,
      payload: selectedSortingMethod,
    });
  };

  const updateUserData = (userData) => {
    return dispatch({
      type: globalActionTypes.UPDATE_USER_DATA,
      payload: userData,
    });
  };

  const updatePostData = (postData) => {
    return dispatch({
      type: globalActionTypes.UPDATE_POST_DATA,
      payload: postData,
    });
  };

  const updateSelectedPost = (selectedPost) => {
    return dispatch({
      type: globalActionTypes.UPDATE_SELECTED_POST,
      payload: selectedPost,
    });
  };

  const updateTheme = (colorTheme) => {
    return dispatch({
      type: globalActionTypes.UPDATE_COLOR_THEME,
      payload: colorTheme,
    });
  };

  const updateOnlineStatus = (isOnline) => {
    return dispatch({
      type: globalActionTypes.UPDATE_ONLINE_STATUS,
      payload: isOnline,
    });
  };

  return (
    <AppContext.Provider
      {...props}
      value={{
        ...state,
        showLoader,
        hideLoader,
        updateSelectedSortingMethod,
        updateSelectedPost,
        updateUserData,
        updatePostData,
        updateTheme,
        updateOnlineStatus,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

AppProvider.propTypes = {
  children: PropTypes.node.isRequired,
};

const useGlobalAppContext = () => {
  const context = useContext(AppContext);

  if (context === undefined)
    throw new Error("useGlobalAppContext must be used within a AppProvider");

  return context;
};

export { AppProvider, useGlobalAppContext };
