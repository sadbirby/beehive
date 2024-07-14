import { Button, LoadingSpinner } from "@/components/ui";
import { useGlobalAppContext } from "@/context/app-context";
import {
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsLeftIcon,
  ChevronsRightIcon,
} from "lucide-react";
import { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { PostCardComponent } from "../post/post-card-component";
import { fetchAllPosts } from "./home-helper";

export function HomeComponent() {
  const {
    loaderEnabled,
    loaderMessage,
    userData,
    postData,
    updateSelectedPage,
    updateSelectedPost,
    updatePostData,
    showLoader,
    hideLoader,
  } = useGlobalAppContext();
  const username = localStorage.getItem("username");
  const [postResponse, setPostResponse] = useState([]);
  const pageIndexRef = useRef(0);

  const getAllPosts = async (pageNumber) => {
    try {
      showLoader();
      await fetchAllPosts(username, pageNumber)
        .then((res) => {
          setPostResponse(res);
          return res.content;
        })
        .then((res) => updatePostData(res));
    } catch (e) {
      console.error("Error in home-component", e);
    } finally {
      console.log("Fetching posts completed");
      hideLoader();
    }
  };

  useEffect(() => {
    const initialise = async () => {
      console.log("Fetching posts");
      await getAllPosts(pageIndexRef.current);
    };
    initialise();
  }, []);

  const onPageIndexChange = async (newIndex) => {
    pageIndexRef.current = newIndex;
    await getAllPosts(newIndex);
  };

  const listItems = postData.map((post) => {
    return (
      <div
        key={post.postId}
        onClick={() => {
          updateSelectedPost(post);
          showLoader();
          // updateSelectedPage(pages.PAGE_REPLY);
        }}
      >
        <Link
          key={post.postId}
          to={`/${post.postedBy}/post/${post.postId}`}
          replace={true}
        >
          <PostCardComponent
            className="cursor-pointer hover:bg-secondary/[25%]"
            lineClamp={"line-clamp-4"}
            post={post}
          />
        </Link>
      </div>
    );
  });

  return loaderEnabled ? (
    <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
    </div>
  ) : (
    <div className="grid grid-cols-1 gap-2">
      {/* <Card className="px-0 py-4"></Card> */}
      <div className="grid grid-cols-1 gap-2">{listItems}</div>
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
          <div className="flex w-1/6 items-center justify-center font-medium">
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
      </div>
    </div>
  );
}
