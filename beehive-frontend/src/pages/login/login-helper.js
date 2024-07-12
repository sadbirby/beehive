import { HttpPost } from "@/utils/api-service";
import { AUTHENTICATE } from "../../constants/endpoints";

export const authenticate = async (username, password) => {
  const url = AUTHENTICATE;
  const body = {
    username: username,
    password: password,
  };

  const response = await HttpPost(url, body, {});
  return response.data.token;
};
