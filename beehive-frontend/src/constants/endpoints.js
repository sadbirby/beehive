export const BASE_URI = "http://localhost:8097";

/* Authentication */
export const AUTHENTICATE = BASE_URI + "/api/v1.0/login/authenticate";

/* User */
export const USER_BASE = "/api/v1.0/user";
export const REGISTER = USER_BASE + "/register";
export const FORGOT_PASSWD = USER_BASE + "/forgot-password";
export const ALL_USERS = USER_BASE + "/all";
export const GET_USER = USER_BASE + "/search/";
export const CHECK_USER = USER_BASE + "/check/";

/* Tweet */
export const POST_BASE = BASE_URI + "/api/v1.0/posts";
export const ALL_POSTS = "/all";
export const POPULAR_TWEETS = POST_BASE + "/popular";
export const REPLY_TO_POST = POST_BASE + "/reply";
