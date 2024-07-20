import { POST_BASE, REPLY_TO_POST } from "@/constants/endpoints";
import { HttpGet, HttpPost } from "@/utils/api-service";

export const fetchAllReplies = async (postId, pageNumber = 0, username) => {
  let apiUrl = `${POST_BASE}/${postId}/replies?username=${username}`;
  let parameters = {
    pageNumber: pageNumber,
  };
  let response = await HttpGet(apiUrl, parameters, {});
  return response.data;
};

export const replyToPost = async (postId, replyBody, repliedBy) => {
  let apiUrl = REPLY_TO_POST;
  let reply = {
    postId: postId,
    replyBody: replyBody,
    repliedBy: repliedBy,
  };
  let response = await HttpPost(apiUrl, { reply: reply }, {});
  return response.data;
};

export const upvoteAReply = async (replyId, username) => {
  let apiUrl = `${POST_BASE}/reply/${replyId}/like?username=${username}`;
  let response = {};
  try {
    response = await HttpPost(apiUrl, {}, {});
  } catch (error) {
    console.error(error);
  }
  return response.data;
};

export const downvoteAReply = async (replyId, username) => {
  let apiUrl = `${POST_BASE}/reply/${replyId}/unlike?username=${username}`;
  let response = {};
  try {
    response = await HttpPost(apiUrl, {}, {});
  } catch (error) {
    console.error(error);
  }
  return response.data;
};
