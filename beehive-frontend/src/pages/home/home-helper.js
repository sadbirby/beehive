import { ALL_POSTS, BASE_URI, GET_USER, POPULAR_TWEETS, POST_BASE } from "@/constants/endpoints";
import { HttpGet, HttpPost } from "@/service/api-service";


export const fetchLoggedInUserDetails = async () => {

    var credentials = "Bearer " + localStorage.getItem("token");
    var apiUrl = BASE_URI + GET_USER + localStorage.getItem("username");
    var headers = {
        "Authorization": credentials,
    }
    return await HttpGet(apiUrl, {}, headers);
}

export const fetchAllPosts = async (username) => {

    var credentials = "Bearer " + localStorage.getItem("token");
    var apiUrl = POST_BASE + `/${username}` + ALL_POSTS;
    var headers = {
        "Authorization": credentials
    };
    var result = '';
    try {
        result = await HttpGet(apiUrl, {}, headers);
    } catch (error) {
        console.log(error);
    }
    
    return result.data.posts;
}

export const fetchPopularTweets = async () => {

    var credentials = "Bearer " + localStorage.getItem("token");
    var apiUrl = POPULAR_TWEETS;
    var headers = {
        "Authorization": credentials
    }
    var response = await HttpGet(apiUrl, {}, headers)
    return response.data.tweetsDto;
}

export const postTweet = async (username, tweetMessage) => {
    var credentials = "Bearer " + localStorage.getItem("token");
    var headers = {
        "Authorization": credentials
    }
    var apiUrl = POST_BASE + "/" + username + "/add";
    await HttpPost(apiUrl, {
        tweet: {
            tweet: tweetMessage,
        }
    }, headers)
}

export const postReplyTweet = async (data) => {
    var credentials = "Bearer " + localStorage.getItem("token");
    var headers = {
        "Authorization": credentials
    }
    var apiUrl = POST_BASE + "/reply";
    await HttpPost(apiUrl, data, headers)
}

export const likeTweet = async (data, username) => {
    var credentials = "Bearer " + localStorage.getItem("token");
    var headers = {
        "Authorization": credentials
    }
    var apiUrl = POST_BASE + "/" + username + "/like";
    await HttpPost(apiUrl, data, headers)
}

export const unlikeTweet = async (data, username) => {
    var credentials = "Bearer " + localStorage.getItem("token");
    var headers = {
        "Authorization": credentials
    }
    var apiUrl = POST_BASE + "/" + username + "/unlike";
    await HttpPost(apiUrl, data, headers)
}
