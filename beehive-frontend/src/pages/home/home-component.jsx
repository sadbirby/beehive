import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";
import { LoadingSpinner } from "@/components/ui/loading-spinner";
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";
import { getRelativeDate } from "@/service/get-relative-date";
import {
  ArrowBigUp,
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsLeftIcon,
  ChevronsRightIcon,
  MessageSquareIcon,
} from "lucide-react";
import { useEffect, useRef, useState } from "react";
import { fetchAllPosts } from "./home-helper";

const HomeComponent = () => {
  const {
    loaderEnabled,
    loaderMessage,
    userData,
    postData,
    updateSelectedPage,
    updatePostData,
    showLoader,
    hideLoader,
  } = useGlobalAppContext();
  const [postResponse, setPostResponse] = useState([]);
  const pageIndexRef = useRef(0);

  useEffect(() => {
    const initialise = async () => {
      await getAllPosts(pageIndexRef.current);
    };
    initialise();
  }, []);

  const getAllPosts = async (pageNumber) => {
    try {
      showLoader("Fetching Posts...");
      setPostResponse(await fetchAllPosts(userData.username, pageNumber));
    } catch (e) {
      console.error("Error in home-component", e);
    } finally {
      hideLoader();
    }
  };

  const onPageIndexChange = async (newIndex) => {
    pageIndexRef.current = newIndex;
    await getAllPosts(newIndex);
  };

  const PostCardComponent = ({ post }) => {
    return (
      <Card className="inline-flex w-full cursor-pointer px-0 py-4 hover:bg-secondary">
        <div className="grid grid-rows-2 place-items-start gap-0 py-0 pl-4 pr-0">
          <div>
            <Avatar className="rounded-none">
              <AvatarFallback className="rounded-none bg-primary/[50%]">
                {post.postedBy.substring(0, 2).toUpperCase()}
              </AvatarFallback>
            </Avatar>
          </div>
        </div>
        <div className="w-full flex-initial">
          <CardHeader className="py-0 pl-4 pr-10">
            <div className="grid grid-cols-2 gap-0">
              <div>
                <p className="text-xs">{post.postedBy}</p>
              </div>
              <div className="place-self-end">
                <CardDescription className="text-right text-xs">
                  {getRelativeDate(post.postedOn)}
                </CardDescription>
              </div>
            </div>
          </CardHeader>
          <CardContent className="py-1 pl-4 pr-10">
            <p className="text-sm font-bold">{post.postTitle}</p>
          </CardContent>
          <CardContent className="line-clamp-6 py-1 pl-4 pr-10">
            <p className="text-sm">{post.postBody}</p>
          </CardContent>
          <CardFooter className="grid grid-cols-2 px-4 pb-0 pt-2">
            <div className="inline-flex gap-4">
              <div className="inline-flex items-center gap-2 border bg-secondary py-1 pl-2 pr-4">
                <ArrowBigUp />
                <p className="text-center text-xs font-extralight">
                  {post.numberOfLikes}
                </p>
              </div>
              <div className="inline-flex items-center gap-2 border bg-secondary py-1 pl-2 pr-4">
                <MessageSquareIcon />
                <p className="text-center text-xs font-extralight">
                  {post.numberOfReplies}
                </p>
              </div>
            </div>
          </CardFooter>
        </div>
      </Card>
    );
  };

  const listItems = postResponse.content?.map((post) => {
    return (
      <div
        key={post.postId}
        onClick={() => {
          updatePostData(post);
          updateSelectedPage(pages.PAGE_REPLY);
        }}
      >
        <PostCardComponent post={post} />
      </div>
    );
  });

  return loaderEnabled ? (
    <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
      <div>
        <h4 className="text-sm font-extralight">{loaderMessage}</h4>
      </div>
    </div>
  ) : (
    <div className="grid grid-cols-1 gap-2">
      <Card className="px-0 py-4"></Card>
      <div className="grid grid-cols-1 gap-4">{listItems}</div>
      <div className="flex-row items-center justify-between gap-2 px-0 py-4">
        <div className="flex items-center justify-center space-x-2">
          <Button
            variant="outline"
            className="h-8 w-8 p-0"
            onClick={() => {
              if (!postResponse.first) {
                onPageIndexChange(0);
              }
            }}
            disabled={postResponse.first}
          >
            <span className="sr-only">Go to first page</span>
            <ChevronsLeftIcon className="h-4 w-4" />
          </Button>
          <Button
            variant="outline"
            className="h-8 w-8 p-0"
            onClick={() => {
              if (!postResponse.first) {
                onPageIndexChange(pageIndexRef.current - 1);
              }
            }}
            disabled={postResponse.first}
          >
            <span className="sr-only">Go to previous page</span>
            <ChevronLeftIcon className="h-4 w-4" />
          </Button>
          <div className="flex w-1/6 items-center justify-center text-sm font-medium">
            Page {postResponse.number + 1} of {postResponse.totalPages}
          </div>
          <Button
            variant="outline"
            className="h-8 w-8 p-0"
            onClick={() => {
              if (!postResponse.last) {
                onPageIndexChange(pageIndexRef.current + 1);
              }
            }}
            disabled={postResponse.last}
          >
            <span className="sr-only">Go to next page</span>
            <ChevronRightIcon className="h-4 w-4" />
          </Button>
          <Button
            variant="outline"
            className="h-8 w-8 p-0"
            onClick={() => {
              if (!postResponse.last) {
                onPageIndexChange(postResponse.totalPages - 1);
              }
            }}
            disabled={postResponse.last}
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
                        disabled={inputRef.current < 1 || inputRef.current > postResponse.totalPages}
                    >Go</Button>
                </div> */}
      </div>
    </div>
  );
};

export default HomeComponent;
