import {
  Avatar,
  AvatarFallback,
  Button,
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
} from "@/components/ui";
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";
import { getRelativeDate } from "@/utils/get-relative-date";
import { ArrowBigUp, MessageSquareIcon } from "lucide-react";
import { useState } from "react";
import { toast } from "sonner";
import { downvoteAPost, upvoteAPost } from "./post-card-helper";

export function PostCardComponent({
  post,
  className,
  lineClamp,
  openModal,
  setOpenModal,
}) {
  const { userData, postData, selectedPage, selectedPost, updateSelectedPost } =
    useGlobalAppContext();
  const [numOfLikes, setNumOfLikes] = useState(post.numberOfLikes);
  const [fillColor, setFillColor] = useState(
    post.postLikedByCurrentUser ? "orange" : "none",
  );

  const onLikeButtonPress = async (e) => {
    e.stopPropagation();
    try {
      if (!post.postLikedByCurrentUser) {
        await upvoteAPost(post.postId, userData.username).then((res) => {
          if (res.statusMessage === "SUCCESS") {
            post.postLikedByCurrentUser = !post.postLikedByCurrentUser;
            setFillColor("orange");
            setNumOfLikes(numOfLikes + 1);
          }
        });
      } else {
        if (numOfLikes > 0) {
          await downvoteAPost(post.postId, userData.username).then((res) => {
            if (res.statusMessage === "SUCCESS") {
              post.postLikedByCurrentUser = !post.postLikedByCurrentUser;
              setFillColor("none");
              setNumOfLikes(numOfLikes - 1);
            }
          });
        }
      }
    } catch (e) {
      toast.error("An Error Occured");
      console.error("Error in post-card-component", e);
    }
  };

  return (
    <Card className={`inline-flex w-full px-0 py-4 ${className}`}>
      <div className="grid grid-rows-2 place-items-start gap-0 py-0 pl-4 pr-0">
        <div>
          <Avatar className="rounded-none border-2">
            <AvatarFallback className="rounded-none bg-secondary/[50%] font-mono">
              {post.postedBy.substring(0, 2).toUpperCase()}
            </AvatarFallback>
          </Avatar>
        </div>
      </div>
      <div className="w-full flex-initial">
        <CardHeader className="py-0 pl-4 pr-10">
          <div className="grid grid-cols-2 gap-0">
            <div>
              <p className="text-foreground/[65%]">{post.postedBy}</p>
            </div>
            <div className="place-self-end">
              <CardDescription className="text-right">
                {getRelativeDate(post.postedOn)}
              </CardDescription>
            </div>
          </div>
        </CardHeader>
        <CardContent className="py-1 pl-4 pr-10">
          <h4 className="font-bold">{post.postTitle}</h4>
        </CardContent>
        <CardContent className={`py-1 pl-4 pr-10 ${lineClamp}`}>
          <p>{post.postBody}</p>
        </CardContent>
        <CardFooter className="grid grid-cols-2 px-4 pb-0 pt-2">
          <div className="inline-flex gap-6">
            <Button
              // disabled={setOpenModal === undefined}
              onClick={onLikeButtonPress}
              className="inline-flex h-8 items-center gap-1 rounded-full border bg-primary/[40%] pl-2 pr-4 hover:bg-primary/[60%]"
            >
              <ArrowBigUp fill={fillColor} strokeWidth={1} />
              <p className="text-center">{numOfLikes}</p>
            </Button>
            <Button
              className="inline-flex h-8 items-center gap-1 rounded-full border bg-primary/[40%] pl-2 pr-4 hover:bg-primary/[60%]"
              onClick={() => {
                if (selectedPage === pages.PAGE_REPLY) {
                  setOpenModal(!openModal);
                }
              }}
            >
              <MessageSquareIcon strokeWidth={2} size={18} />
              <p className="text-center">{post.numberOfReplies}</p>
            </Button>
          </div>
        </CardFooter>
      </div>
    </Card>
  );
}
