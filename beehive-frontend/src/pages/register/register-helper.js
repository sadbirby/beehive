import { HttpGet, HttpPost } from "@/utils/api-service";
import { BASE_URI, CHECK_USER, REGISTER } from "../../constants/endpoints";

export const checkIfUserExists = async (username) => {
  let apiUrl = BASE_URI + CHECK_USER + username;
  const doesUserExist = await HttpGet(apiUrl, {}, {});
  return doesUserExist.data.doesUserExist;
};

export const register = async (username, email, password) => {
  const url = BASE_URI + REGISTER;
  const body = {
    user: {
      username: username,
      email: email,
      password: password,
    },
  };

  const response = await HttpPost(url, body, {});
  return response.data;
};
