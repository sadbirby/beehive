package com.beehive.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.beehive.dto.ReplyDto;
import com.beehive.dto.TweetsDto;
import com.beehive.entity.TweetsEntity;
import com.beehive.repository.TweetsRepository;
import com.beehive.request.TweetRequest;
import com.beehive.response.TweetResponse;
import com.beehive.service.TweetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetsServiceImpl implements TweetsService {

    @Autowired TweetsRepository tweetsRepository;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
    SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public TweetResponse getAllTweets() {

        TweetResponse response = new TweetResponse();
        try {
            Iterable<TweetsEntity> tweets = tweetsRepository.findAll();
            List<TweetsDto> tweetsDto = new ArrayList<>();
            tweets.forEach(entity -> {
                TweetsDto tweet = new TweetsDto();
                tweet.setTweet(entity.getTweet());
                tweet.setTweetId(entity.getTweetId());
                tweet.setUserTweetId(entity.getUserTweetId());
                tweet.setLike(entity.getLikes());
                tweet.setReply(entity.getReply());
                tweet.setDateOfPost(simpleDateFormat.format(entity.getDateOfPost()));
                tweet.setTimeOfPost(localDateFormat.format(entity.getDateOfPost()));
                tweetsDto.add(tweet);
            });
            response.setTweetsDto(tweetsDto);
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            response.setStatusMessage("FAILURE");
        }

        return response;
    }

    @Override
    public TweetResponse getAllTweetsByUserName(String userName) {

        TweetResponse response = new TweetResponse();
        List<TweetsDto> tweetsDto = new ArrayList<>();
        try {
            List<TweetsEntity> tweets = tweetsRepository.findByUserTweetId(userName);
            tweets.forEach(entities -> {
                TweetsDto dto = new TweetsDto();
                dto.setTweet(entities.getTweet());
                dto.setLike(entities.getLikes());
                dto.setReply(entities.getReply());
                dto.setTweetId(entities.getTweetId());
                dto.setUserTweetId(entities.getUserTweetId());
                dto.setDateOfPost(simpleDateFormat.format(entities.getDateOfPost()));
                dto.setTimeOfPost(localDateFormat.format(entities.getDateOfPost()));
                tweetsDto.add(dto);
            });
            response.setStatusMessage("SUCCESS");
            response.setTweetsDto(tweetsDto);
        } catch (
                Exception e) {
            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }

        return response;
    }

    @Override
    public TweetResponse addTweet(TweetRequest request, String userName) {

        TweetResponse response = new TweetResponse();
        TweetsDto tweet = request.getTweet();
        TweetsEntity entity = new TweetsEntity();
        List<ReplyDto> reply = new ArrayList<>();
        try {
            entity.setTweet(tweet.getTweet());
            entity.setUserTweetId(userName);
            entity.setLikes(0L);
            entity.setReply(reply);
            entity.setDateOfPost(new Date());
            tweetsRepository.save(entity);
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    public TweetResponse deleteTweet(String userName, String tweetId) {

        TweetResponse response = new TweetResponse();
        try {
            tweetsRepository.deleteById(tweetId);
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    public TweetResponse replyToTweet(TweetRequest request) {

        TweetResponse response = new TweetResponse();
        TweetsDto dto = request.getTweet();
        List<ReplyDto> replies;
        try {
            Optional<TweetsEntity> entity = tweetsRepository.findById(dto.getTweetId());
            if (entity.isPresent()) {
                replies = new ArrayList<>(entity.get().getReply());
            } else {
                throw new NullPointerException();
            }
            List<ReplyDto> newReplies = new ArrayList<>();
            if (dto.getReply() != null) {
                newReplies = dto.getReply();
            } else {
                throw new NullPointerException();
            }
            newReplies.forEach(reply -> {
                reply.setDateReplied(new Date());
            });
            replies.addAll(newReplies);
            entity.get().setReply(replies);
            tweetsRepository.save(entity.get());
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    public TweetResponse likeATweet(TweetRequest request) {

        TweetResponse response = new TweetResponse();
        TweetsDto dto = request.getTweet();
        try {
            Optional<TweetsEntity> entity = tweetsRepository.findById(dto.getTweetId());
            if (entity.isPresent() && entity.get().getLikes() != null) {
                entity.get().setLikes(entity.get().getLikes() + 1);
            }
            tweetsRepository.save(entity.get());
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {

            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    public TweetResponse unlikeATweet(TweetRequest request) {

        TweetResponse response = new TweetResponse();
        TweetsDto dto = request.getTweet();
        try {
            Optional<TweetsEntity> entity = tweetsRepository.findById(dto.getTweetId());
            if (entity.isPresent() && entity.get().getLikes() != null) {
                entity.get().setLikes(entity.get().getLikes() - 1);
            }
            tweetsRepository.save(entity.get());
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {

            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    public TweetResponse updateTweet(TweetRequest request) {

        TweetResponse response = new TweetResponse();
        TweetsDto dto = request.getTweet();
        try {
            Optional<TweetsEntity> entity = tweetsRepository.findById(dto.getTweetId());
            entity.get().setTweet(dto.getTweet());
            tweetsRepository.save(entity.get());
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {

            e.printStackTrace();
            response.setStatusMessage("FAILURE");
        }
        return response;
    }
}
