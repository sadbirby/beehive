import { POST_BASE } from "@/constants/endpoints";
import { HttpPost } from "@/service/api-service";

export const createPost = async (username, post) => {
  let apiUrl = POST_BASE + "/" + username + "/add";
  let response = {};
  try {
    response = await HttpPost(apiUrl, { post: post }, {});
  } catch (error) {
    console.log(error);
  }
  return response.data;
};
