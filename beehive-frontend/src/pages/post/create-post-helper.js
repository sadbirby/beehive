import { POST_BASE } from "@/constants/endpoints";
import { HttpPost } from "@/utils/api-service";

export const createPost = async (username, post) => {
  let apiUrl = `${POST_BASE}/add?username=${username}`;
  let response = {};
  try {
    response = await HttpPost(apiUrl, { post: post }, {});
  } catch (error) {
    console.error(error);
  }
  return response.data;
};
