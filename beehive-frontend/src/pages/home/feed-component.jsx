import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";
import { useGlobalAppContext } from "@/context/app-context";
import { useEffect, useState } from "react";
import {
  fetchAllPosts,
  likeTweet,
  postReplyTweet,
  unlikeTweet,
} from "./home-helper";
// import PopularPostsComponent from './popular-posts-component';
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import { ArrowBigUp, MessageSquareIcon } from "lucide-react";

// import FeedComponent from './feed-component';

const HomeComponent = () => {
  const { selectedPage, userData, showLoader, hideLoader } =
    useGlobalAppContext();
  const [tweetList, setTweetList] = useState([]);

  const getAllPosts = async () => {
    try {
      showLoader("fetching All Posts");
      setTweetList(await fetchAllPosts(userData.username));
      console.log("GetAllPosts", tweetList);
      hideLoader();
    } catch (e) {
      hideLoader();
    }
  };

  useEffect(() => {
    console.log("INSIDE HOME.JSX USEEFFECT");
    const initialise = async () => {
      await getAllPosts();
    };
    initialise();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedPage]);

  const generatePosts = () => {
    return tweetList.map((tweet) => {
      const date1 = new Date();
      const date2 = new Date(tweet.dateOfPost);
      const diffTime = Math.abs(date2 - date1);

      let diffDays = Math.ceil(diffTime / (1000 * 60));
      let units = "mins";
      let userTweetId = tweet.userTweetId;
      let tweetId = tweet.tweetId;
      let likedByCurrentUser = tweet.likedBy.includes(userData.loginId);

      if (diffDays > 60) {
        diffDays = (diffDays / 60).toFixed();
        units = "hours";
        if (diffDays > 24) {
          diffDays = (diffDays / 24).toFixed();
          units = diffDays > 1 ? "days" : "day";
        }
      }

      const onLikeClick = async () => {
        try {
          console.log("IS LIKED BY CURRENT USER?: ", likedByCurrentUser);
          if (likedByCurrentUser) {
            await unlikeTweet(
              { tweet: { tweetId: tweetId } },
              userData.loginId,
            );
            tweet.like = tweet.like - 1;
            tweet.likedBy = tweet.likedBy.filter(
              (id) => id !== userData.loginId,
            );
            setTweetList(tweetList);
          } else {
            await likeTweet({ tweet: { tweetId: tweetId } }, userData.loginId);
            tweet.like = tweet.like + 1;
            tweet.likedBy = [...tweet.likedBy, userData.loginId];
            setTweetList(tweetList);
          }
          likedByCurrentUser = !likedByCurrentUser;
          likeIndicator();
        } catch (e) {
          console.log(e);
        }
      };

      const likeIndicator = () => {
        return likedByCurrentUser ? (
          <ArrowBigUp color="teal" fill="teal" />
        ) : (
          <ArrowBigUp />
        );
      };

      const onReplyClick = () => {
        let tweets = [...tweetList];
        tweet.showReplies = !tweet.showReplies;
        setTweetList(tweets);
      };

      let replyMessage = "";
      const onChangeText = (e) => {
        replyMessage = e.target.value;
      };

      const onReplyTweet = async () => {
        try {
          showLoader("Posting Reply");
          await postReplyTweet({
            tweet: {
              userTweetId: userTweetId,
              tweetId: tweetId,
              reply: [
                {
                  userId: userData.loginId,
                  replied: replyMessage,
                },
              ],
            },
          });

          let tweetList = await fetchAllPosts();
          setTweetList(tweetList);
          hideLoader();
        } catch (e) {
          hideLoader();
        }
      };

      return (
        <div key={tweet.tweetId}>
          <Separator className="mb-3" />
          <Card className="inline-flex w-full border-none px-0 py-2 hover:bg-secondary/[20%]">
            <div className="grid grid-rows-2 place-items-center gap-0 py-0 pl-4 pr-0">
              <div>
                <Avatar>
                  <AvatarFallback>{tweet.userTweetId.charAt(0)}</AvatarFallback>
                </Avatar>
              </div>
            </div>
            <div className="w-full flex-initial">
              <CardHeader className="px-4 py-0">
                <div className="grid grid-cols-2 gap-0">
                  <div>
                    <p className="font-extrabold">@{tweet.userTweetId}</p>
                  </div>
                  <div className="place-self-end">
                    <CardDescription className="text-right">
                      {diffDays} {units} ago
                    </CardDescription>
                  </div>
                </div>
              </CardHeader>
              <CardContent
                onClick={onReplyClick}
                className="cursor-pointer px-4 py-1"
              >
                <p className="font-thin">{tweet.tweet}</p>
              </CardContent>
              <CardFooter className="grid grid-cols-2 px-4 py-0">
                <div className="inline-flex gap-4">
                  <div
                    className="inline-flex cursor-pointer items-center gap-2 rounded-sm border bg-secondary py-1 pl-2 pr-4"
                    onClick={onLikeClick}
                  >
                    {likeIndicator()}
                    <p className="text-center text-xs font-extralight">
                      {tweet.like}
                    </p>
                  </div>
                  <div
                    className="inline-flex cursor-pointer items-center gap-2 rounded-sm border bg-secondary py-1 pl-2 pr-4"
                    onClick={onLikeClick}
                  >
                    <MessageSquareIcon />
                    <p className="text-center text-xs font-extralight">
                      {tweet.reply.length}
                    </p>
                  </div>
                </div>
              </CardFooter>
              <div>
                {tweet.showReplies && (
                  <>
                    <div>
                      {tweet.reply.map((reply, replyIndex) => {
                        const replydate1 = new Date();
                        const replydate2 = new Date(reply.dateReplied);
                        const replydiffTime = Math.abs(replydate2 - replydate1);
                        let replydiffDays = Math.ceil(
                          replydiffTime / (1000 * 60),
                        );
                        let units = "mins";
                        if (replydiffDays > 60) {
                          replydiffDays = (replydiffDays / 60).toFixed();
                          units = "hours";
                          if (replydiffDays > 24) {
                            replydiffDays = (replydiffDays / 24).toFixed();
                            units = replydiffDays > 1 ? "days" : "day";
                          }
                        }
                        return (
                          <div key={replyIndex}>
                            <Avatar>
                              <AvatarFallback>
                                {reply.userId.charAt(0)}
                              </AvatarFallback>
                            </Avatar>
                            <div>
                              <p>
                                {reply.userId}{" "}
                                <span>
                                  {replydiffDays} {units} ago
                                </span>
                              </p>
                              <p>{reply.replied}</p>
                            </div>
                          </div>
                        );
                      })}
                    </div>
                    <div>
                      <p>
                        You are replying to <span>{tweet.userTweetId}</span>
                      </p>
                      <div>
                        <Avatar>
                          <AvatarFallback>
                            {tweet.userTweetId.charAt(0)}
                          </AvatarFallback>
                        </Avatar>
                        <textarea
                          placeholder={"Post your reply"}
                          multiple={4}
                          maxLength={144}
                          onChange={onChangeText}
                        />
                      </div>

                      <div>
                        <Button onClick={onReplyTweet}>Reply</Button>
                      </div>
                    </div>
                  </>
                )}
              </div>
            </div>
          </Card>
        </div>
      );
    });
  };

  return (
    <div className="grid gap-2 lg:grid-cols-[1fr_360px]">
      <div className="grid grid-cols-1 gap-3">
        <Card className="px-0 py-4"></Card>
        <div>{generatePosts}</div>
      </div>
      {/* <PopularPostsComponent /> */}
    </div>
  );
};

export default HomeComponent;
