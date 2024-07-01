import { Card, CardDescription, CardHeader } from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";
import { useEffect, useState } from "react";
import { fetchPopularTweets } from "./home-helper";

const PopularPostsComponent = () => {
  const { selectedPage, showLoader, hideLoader } = useGlobalAppContext();
  const [popularTweetList, setPopularTweetList] = useState([]);

  useEffect(() => {
    const initialise = async (loaderMessage) => {
      try {
        if (selectedPage === pages.PAGE_HOME) {
          showLoader(loaderMessage);
          let fetchedTweets = await fetchPopularTweets();
          setPopularTweetList(fetchedTweets);
        }
      } catch (e) {
        console.error("Error in pop", e);
      } finally {
        hideLoader();
      }
    };
    initialise("Fetching All Posts");
  }, []);

  const onReplyClick = (event) => {
    event.preventDefault();
  };

  return (
    <div className="hidden lg:block">
      <Card className="rounded-sm px-0">
        <CardHeader className="bg-muted/40 p-4">Trending Posts</CardHeader>
        <div className="grid grid-cols-1">
          {popularTweetList.map((tweet) => (
            <div
              key={tweet.tweetId}
              className="cursor-pointer hover:bg-muted/80"
              onClick={onReplyClick}
            >
              <Separator className="p-0" />
              <CardDescription className="truncate p-4">
                {tweet.userTweetId}: {tweet.tweet}
              </CardDescription>
            </div>
          ))}
        </div>
      </Card>
    </div>
  );
};

export default PopularPostsComponent;
