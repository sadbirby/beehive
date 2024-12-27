import {
  Button,
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuRadioGroup,
  DropdownMenuRadioItem,
  DropdownMenuTrigger,
  LoadingSpinner,
  Separator,
} from "@/components/ui";
import { useGlobalAppContext } from "@/context/app-context";
import {
  ArrowDownAZ,
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsLeftIcon,
  ChevronsRightIcon,
} from "lucide-react";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { PostCardComponent } from "../post/post-card-component";
import { fetchAllPosts } from "./home-helper";

const HomeComponent = () => {
  const {
    selectedSortingMethod,
    loaderEnabled,
    postData,
    updateSelectedPost,
    updateSelectedSortingMethod,
    updatePostData,
    showLoader,
    hideLoader,
  } = useGlobalAppContext();
  const username = localStorage.getItem("username");
  const [postResponse, setPostResponse] = useState([]);
  const [pageIndex, setPageIndex] = useState(0);
  let pageSizeRef = useRef(10);
  let sortByRef = useRef("postedOn");
  let isDescendingRef = useRef(true);

  const getAllPosts = useCallback(
    async (pageNumber, pageSize, sortBy, isDescending) => {
      try {
        showLoader();
        await fetchAllPosts(
          username,
          pageNumber,
          pageSize,
          sortBy,
          isDescending,
        )
          .then((res) => {
            setPostResponse(res);
            return res.content;
          })
          .then((res) => updatePostData(res));
      } catch (e) {
        console.error("Error in home-component", e);
      } finally {
        hideLoader();
      }
    },
    [selectedSortingMethod],
  );

  const memoizedGetAllPosts = useMemo(() => getAllPosts, [getAllPosts]);

  useEffect(() => {
    const initialise = async () => {
      switch (selectedSortingMethod) {
        case "Newest":
          sortByRef.current = "postedOn";
          isDescendingRef.current = true;
          break;
        case "Oldest":
          sortByRef.current = "postedOn";
          isDescendingRef.current = false;
          break;
        case "Popular":
          sortByRef.current = "numberOfLikes";
          isDescendingRef.current = true;
          break;
        default:
          sortByRef.current = "postedOn";
          isDescendingRef.current = true;
      }
      await memoizedGetAllPosts(
        pageIndex,
        pageSizeRef.current,
        sortByRef.current,
        isDescendingRef.current,
      );
    };
    initialise();
  }, [selectedSortingMethod, pageIndex]);

  const listItems = postData.map((post) => {
    return (
      <div
        key={post.postId}
        onClick={() => {
          updateSelectedPost(post);
          showLoader();
        }}
      >
        <Link
          key={post.postId}
          to={`/${post.postedBy}/post/${post.postId}`}
          replace={true}
        >
          <PostCardComponent
            className="cursor-pointer border-none shadow-none hover:bg-secondary/[25%]"
            lineClamp={"line-clamp-4"}
            post={post}
          />
        </Link>
        <Separator className="my-1" />
      </div>
    );
  });

  const PaginationComponent = () => {
    return (
      <div className="flex-row items-center justify-between gap-2 px-0 py-4">
        <div className="flex items-center justify-center space-x-2">
          <Button
            variant="outline"
            className="h-8 w-8 p-0"
            onClick={() => {
              if (!postResponse.first) {
                setPageIndex(0);
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
                setPageIndex(pageIndex - 1);
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
                setPageIndex(pageIndex + 1);
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
                setPageIndex(postResponse.totalPages - 1);
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
    );
  };

  return loaderEnabled ? (
    <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
    </div>
  ) : (
    <div className="grid w-full max-w-5xl grid-cols-1 self-center rounded-md border bg-card shadow-sm">
      {/* <Card className="px-0 py-4"></Card> */}
      <div className="justify-self-start p-2">
        <DropdownMenu modal={false}>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="inline-flex gap-2">
              <ArrowDownAZ />
              Sorted By: {selectedSortingMethod}
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent>
            <DropdownMenuRadioGroup
              value={selectedSortingMethod}
              onValueChange={updateSelectedSortingMethod}
            >
              <DropdownMenuRadioItem value="Newest">
                Newest
              </DropdownMenuRadioItem>
              <DropdownMenuRadioItem value="Oldest">
                Oldest
              </DropdownMenuRadioItem>
              <DropdownMenuRadioItem value="Popular">
                Popular
              </DropdownMenuRadioItem>
            </DropdownMenuRadioGroup>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
      <Separator className="mb-1" />
      <div className="grid grid-cols-1">{listItems}</div>
      <PaginationComponent />
    </div>
  );
};

export default HomeComponent;
