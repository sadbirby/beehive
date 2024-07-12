import { Button, LoadingSpinner } from "@/components/ui/";
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";
import {
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsLeftIcon,
  ChevronsRightIcon,
} from "lucide-react";
import { useEffect, useRef, useState } from "react";
import { PostCardComponent } from "../post/post-card-component";
import { fetchAllPostsByUser } from "./my-profile-helper";

export function MyProfileComponent() {
  const {
    loaderEnabled,
    loaderMessage,
    userData,
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
      setPostResponse(await fetchAllPostsByUser(userData.username, pageNumber));
    } catch (e) {
      console.error("Error in my-profile-component", e);
    } finally {
      hideLoader();
    }
  };

  const onPageIndexChange = async (newIndex) => {
    pageIndexRef.current = newIndex;
    await getAllPosts(newIndex);
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
        <PostCardComponent
          className="cursor-pointer hover:bg-secondary/[25%]"
          lineClamp={"line-clamp-4"}
          post={post}
        />
      </div>
    );
  });

  return loaderEnabled ? (
    <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
      <div>
        <h4 className="font-extralight">{loaderMessage}</h4>
      </div>
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
