import { globalActionTypes } from "./action-types";

export const reducer = (state, action) => {
  switch (action.type) {
    case globalActionTypes.SHOW_LOADER: {
      return {
        ...state,
        loaderEnabled: true,
        loaderMessage: action.payload,
      };
    }
    case globalActionTypes.HIDE_LOADER: {
      return {
        ...state,
        loaderEnabled: false,
        loaderMessage: "",
      };
    }
    case globalActionTypes.UPDATE_SELECTED_PAGE: {
      return {
        ...state,
        selectedPage: action.payload,
      };
    }
    case globalActionTypes.UPDATE_USER_DATA: {
      return {
        ...state,
        userData: action.payload,
      };
    }
    case globalActionTypes.UPDATE_POST_DATA: {
      return {
        ...state,
        postData: action.payload,
      };
    }
    case globalActionTypes.UPDATE_COLOR_THEME: {
      return {
        ...state,
        colorTheme: action.payload,
      };
    }
    default:
      return state;
  }
};
