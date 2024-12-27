import {
  Avatar,
  AvatarFallback,
  Button,
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
  Input,
  LoadingSpinner,
  Separator,
} from "@/components/ui";
import { useGlobalAppContext } from "@/context/app-context";
import { getRelativeDate } from "@/utils/get-relative-date";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsLeftIcon,
  ChevronsRightIcon,
  Heart,
  Loader2,
} from "lucide-react";
import { useEffect, useMemo, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import { useParams } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";
import { PostCardComponent } from "../post/post-card-component";
import { fetchPostById } from "../post/post-card-helper";
import {
  downvoteAReply,
  fetchAllReplies,
  replyToPost,
  upvoteAReply,
} from "./reply-helper";

const FormSchema = z.object({
  replyBody: z.string().min(1, {
    message: "Reply cannot be empty!",
  }),
});

const ReplyComponent = () => {
  const { loaderEnabled, showLoader, hideLoader } = useGlobalAppContext();
  const { postId } = useParams();
  const [currentPost, setCurrentPost] = useState({});
  const [replyResponse, setReplyResponse] = useState({});
  const [replyList, setReplyList] = useState([]);
  const pageIndexRef = useRef(0);
  const username = localStorage.getItem("username");
  // const inputRef = useRef(1);

  const form = useForm({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      replyBody: "",
    },
  });

  const getPostById = useMemo(() => {
    return async () => {
      try {
        await fetchPostById(postId, username).then((res) => {
          setCurrentPost(res);
        });
      } catch (e) {
        console.error("Error in reply-component getPostById()");
      }
    };
  }, [postId]);

  const getAllReplies = async (pageNumber) => {
    try {
      await fetchAllReplies(postId, pageNumber, username)
        .then((res) => {
          setReplyResponse(res);
          return res.content;
        })
        .then((res) => setReplyList(res));
    } catch (e) {
      console.error("Error in reply-component getAllReplies()");
    } finally {
      hideLoader();
    }
  };

  useEffect(() => {
    const initialise = async () => {
      try {
        await getPostById();
        await getAllReplies(pageIndexRef.current);
      } catch (error) {
        console.error("Failed to initialise ReplyComponent");
      } finally {
        hideLoader();
      }
    };
    initialise();
  }, [postId]);

  const onSubmit = async (data) => {
    try {
      showLoader();
      await replyToPost(postId, data.replyBody, username).then((res) => {
        if (res.statusMessage === "SUCCESS") {
          toast.success("Reply Added");
          form.reset();
          onPageIndexChange(0);
        }
      });
    } catch (e) {
      toast.error("Cannot Reply To Post");
      console.error("Error in reply-area-component", e);
    } finally {
      hideLoader();
    }
  };

  const onPageIndexChange = async (newIndex) => {
    showLoader();
    pageIndexRef.current = newIndex;
    await getAllReplies(newIndex);
  };

  const ReplyFormComponent = () => {
    return (
      <div className="rounded-md border bg-background shadow-sm">
        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(onSubmit)}
            className="space-y-2 p-4"
          >
            <FormField
              control={form.control}
              name="replyBody"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <Input
                      className="h-4 border-none focus:h-16"
                      placeholder="Add A Reply"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <div className="flex flex-row-reverse">
              {loaderEnabled ? (
                <Button disabled className="w-1/6">
                  <Loader2 className="mr-2 animate-spin" />
                </Button>
              ) : (
                <Button className="w-1/6" type="submit">
                  Reply
                </Button>
              )}
            </div>
          </form>
        </Form>
      </div>
    );
  };

  const listItems = replyList.map((reply, index, { length }) => {
    return (
      <div key={reply.replyId}>
        <ReplyCardComponent reply={reply} />
        <Separator hidden={index + 1 === length} />
      </div>
    );
  });

  return loaderEnabled ? (
    <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
    </div>
  ) : (
    <div className="grid w-full max-w-5xl grid-cols-1 gap-2 self-center">
      <div className="grid grid-cols-1 gap-3">
        <PostCardComponent post={currentPost.posts?.[0]} className={""} />
        <ReplyFormComponent />
        {replyResponse.totalElements > 0 ? (
          <>
            <div className="grid grid-cols-1 gap-0 rounded-md border bg-background p-0 shadow">
              {listItems}
            </div>
            <div className="flex-row items-center justify-between gap-2 px-0 py-4">
              <div className="flex items-center justify-center space-x-2">
                <Button
                  variant="outline"
                  className="h-8 w-8 p-0"
                  onClick={() => {
                    if (!replyResponse.first) {
                      onPageIndexChange(0);
                    }
                  }}
                  disabled={replyResponse.first}
                >
                  <span className="sr-only">Go to first page</span>
                  <ChevronsLeftIcon className="h-4 w-4" />
                </Button>
                <Button
                  variant="outline"
                  className="h-8 w-8 p-0"
                  onClick={() => {
                    if (!replyResponse.first) {
                      onPageIndexChange(pageIndexRef.current - 1);
                    }
                  }}
                  disabled={replyResponse.first}
                >
                  <span className="sr-only">Go to previous page</span>
                  <ChevronLeftIcon className="h-4 w-4" />
                </Button>
                <div className="flex w-1/6 items-center justify-center">
                  Page {replyResponse.number + 1} of {replyResponse.totalPages}
                </div>
                <Button
                  variant="outline"
                  className="h-8 w-8 p-0"
                  onClick={() => {
                    if (!replyResponse.last) {
                      onPageIndexChange(pageIndexRef.current + 1);
                    }
                  }}
                  disabled={replyResponse.last}
                >
                  <span className="sr-only">Go to next page</span>
                  <ChevronRightIcon className="h-4 w-4" />
                </Button>
                <Button
                  variant="outline"
                  className="h-8 w-8 p-0"
                  onClick={() => {
                    if (!replyResponse.last) {
                      onPageIndexChange(replyResponse.totalPages - 1);
                    }
                  }}
                  disabled={replyResponse.last}
                >
                  <span className="sr-only">Go to last page</span>
                  <ChevronsRightIcon className="h-4 w-4" />
                </Button>
              </div>
              {/* Todo: jump to page */}
              {/* <div className="flex items-center justify-center w-full gap-2">
                    <p className="text-xs font-bold">Jump To</p>
                    <Input
                        className="h-8 w-32 "
                        type="number"
                        onChange={e => inputRef.current = e.target.value}
                    />
                    <Button
                        className="h-8 w-8 "
                        onClick={() => onPageIndexChange(inputRef.current - 1)}
                        disabled={inputRef.current < 1 || inputRef.current > replyResponse.totalPages}
                    >Go</Button>
                </div> */}
            </div>
          </>
        ) : (
          <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
            <p>This post has no replies.</p>
          </div>
        )}
      </div>
    </div>
  );
};

const ReplyCardComponent = ({ reply }) => {
  const [numOfLikes, setNumOfLikes] = useState(reply.numberOfLikes);
  const [fillColor, setFillColor] = useState(
    reply.replyLikedByCurrentUser ? "crimson" : "none",
  );
  const username = localStorage.getItem("username");

  const onLikeButtonPress = async (e) => {
    e.preventDefault();
    e.stopPropagation();
    try {
      if (!reply.replyLikedByCurrentUser) {
        await upvoteAReply(reply.replyId, username).then((res) => {
          if (res.statusMessage === "SUCCESS") {
            reply.replyLikedByCurrentUser = !reply.replyLikedByCurrentUser;
            setFillColor("crimson");
            setNumOfLikes(numOfLikes + 1);
          }
        });
      } else {
        if (numOfLikes > 0) {
          await downvoteAReply(reply.replyId, username).then((res) => {
            if (res.statusMessage === "SUCCESS") {
              reply.replyLikedByCurrentUser = !reply.replyLikedByCurrentUser;
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
    <Card className="inline-flex w-full border-none px-0 py-4 shadow-none">
      <div className="grid grid-rows-2 place-items-start gap-0 py-0 pl-4 pr-0">
        <div>
          <Avatar className="rounded-md border-2">
            <AvatarFallback className="rounded-md bg-secondary/[50%] font-mono">
              {reply.repliedBy.substring(0, 2).toUpperCase()}
            </AvatarFallback>
          </Avatar>
        </div>
      </div>
      <div className="w-full flex-initial">
        <CardHeader className="py-0 pl-4 pr-10">
          <div className="grid grid-cols-2 gap-0">
            <div>
              <p className="text-foreground/[65%]">{reply.repliedBy}</p>
            </div>
            <div className="place-self-end">
              <CardDescription className="text-right text-xs">
                {getRelativeDate(reply.repliedOn)}
              </CardDescription>
            </div>
          </div>
        </CardHeader>
        <CardContent className="py-1 pl-4 pr-10">
          <p>{reply.replyBody}</p>
        </CardContent>
        <CardFooter className="grid grid-cols-2 px-4 pb-0 pt-2">
          <div className="inline-flex gap-6">
            <Button
              variant="secondary"
              onClick={onLikeButtonPress}
              className="inline-flex h-8 items-center gap-2 border pl-2 pr-3"
            >
              <Heart
                size={20}
                color={fillColor !== "none" ? fillColor : "currentColor"}
                fill={fillColor}
                strokeWidth={3}
              />
              <p className="text-center">{numOfLikes}</p>
            </Button>
          </div>
        </CardFooter>
      </div>
    </Card>
  );
};

export default ReplyComponent;
