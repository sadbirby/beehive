package com.beehive.controller;

import com.beehive.request.TweetRequest;
import com.beehive.response.TweetResponse;
import com.beehive.service.TweetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1.0/tweets")
public class TweetController {

    @Autowired
    TweetsService tweetsService;

    private final Logger logger = LoggerFactory.getLogger(TweetController.class);

    @GetMapping(value = "/all")
    public TweetResponse getAllTweets() {
        TweetResponse response = tweetsService.getAllTweets();
        logger.info("Tweet Controller" + " in get All Tweets() call " + response.getStatusMessage());
        return response;
    }

    @GetMapping(value = "/{username}")
    public TweetResponse getAllTweetsUser(@PathVariable("username") String userName) {
        TweetResponse response = tweetsService.getAllTweetsByUserName(userName);
        logger.info("Tweet Controller" + " in get All Tweets() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/{username}/add")
    public TweetResponse addTweet(@RequestBody TweetRequest request, @PathVariable("username") String userName) {
        //producer.sendMessage(request.getTweet().getTweet());
        TweetResponse response = tweetsService.addTweet(request, userName);
        logger.info("Tweet Controller" + " in addTweet() call " + response.getStatusMessage());
        return response;
    }

    @DeleteMapping(path = "/{username}/delete/{id}")
    public TweetResponse deleteTweet(@PathVariable("username") String userName, @PathVariable("id") String tweetId) {
        TweetResponse response = tweetsService.deleteTweet(userName, tweetId);
        logger.info("Tweet Controller" + " in deleteTweet() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/reply")
    public TweetResponse replyToTweet(@RequestBody TweetRequest request) {
        //producer.sendMessage(request.getTweet().getReply().get(0).getReplied());
        TweetResponse response = tweetsService.replyToTweet(request);
        logger.info("Tweet Controller" + " in replyToTweet() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/like")
    public TweetResponse likeATweet(@RequestBody TweetRequest request) {
        TweetResponse response = tweetsService.likeATweet(request);
        logger.info("Tweet Controller" + " in likeATweet() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/unlike")
    public TweetResponse unlikeATweet(@RequestBody TweetRequest request) {
        TweetResponse response = tweetsService.unlikeATweet(request);
        logger.info("Tweet Controller" + " in unlikeATweet() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/update")
    public TweetResponse updateTweet(@RequestBody TweetRequest request) {
        //producer.sendMessage(request.getTweet().getTweet());
        TweetResponse response = tweetsService.updateTweet(request);
        logger.info("Tweet Controller" + " in updateTweet() call " + response.getStatusMessage());
        return response;
    }
}
