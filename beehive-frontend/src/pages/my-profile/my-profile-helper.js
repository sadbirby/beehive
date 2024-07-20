import { POST_BASE } from "@/constants/endpoints";
import { HttpGet } from "@/utils/api-service";

export const fetchAllPostsByUser = async (username, pageNumber = 0) => {
  let apiUrl = `${POST_BASE}/${username}/all`;
  let parameters = {
    pageNumber: pageNumber,
  };
  let response = {};
  try {
    response = await HttpGet(apiUrl, parameters, {});
  } catch (error) {
    console.error(error);
  }
  return response.data;
};
