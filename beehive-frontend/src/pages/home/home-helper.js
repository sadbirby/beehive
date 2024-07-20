import {
  ALL_POSTS,
  BASE_URI,
  GET_USER,
  POPULAR_TWEETS,
  POST_BASE,
} from "@/constants/endpoints";
import { HttpGet, HttpPost } from "@/utils/api-service";

export const fetchLoggedInUserDetails = async (username) => {
  let apiUrl = BASE_URI + GET_USER + username;
  return await HttpGet(apiUrl, {}, {});
};

export const fetchAllPosts = async (
  username,
  pageNumber,
  pageSize,
  sortBy,
  isDescending,
) => {
  let apiUrl = `${POST_BASE}${ALL_POSTS}`;
  let parameters = {
    username: username,
    pageNumber: pageNumber,
    pageSize: pageSize,
    sortBy: sortBy,
    isDescending: isDescending,
  };
  let response = {};
  try {
    response = await HttpGet(apiUrl, parameters, {});
  } catch (error) {
    console.error(error);
  }
  return response.data;
};

export const fetchPopularTweets = async () => {
  let apiUrl = POPULAR_TWEETS;
  let response = await HttpGet(apiUrl, {}, {});
  return response.data.tweetsDto;
};

export const postReplyTweet = async (data) => {
  let credentials = "Bearer " + localStorage.getItem("token");
  let headers = {
    Authorization: credentials,
  };
  let apiUrl = POST_BASE + "/reply";
  await HttpPost(apiUrl, data, headers);
};

export const likeTweet = async (data, username) => {
  let credentials = "Bearer " + localStorage.getItem("token");
  let headers = {
    Authorization: credentials,
  };
  let apiUrl = POST_BASE + "/" + username + "/like";
  await HttpPost(apiUrl, data, headers);
};

export const unlikeTweet = async (data, username) => {
  let credentials = "Bearer " + localStorage.getItem("token");
  let headers = {
    Authorization: credentials,
  };
  let apiUrl = POST_BASE + "/" + username + "/unlike";
  await HttpPost(apiUrl, data, headers);
};
