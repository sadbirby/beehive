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
    loaderEnabled,
    postData,
    updateSelectedPost,
    updatePostData,
    showLoader,
    hideLoader,
  } = useGlobalAppContext();
  const username = localStorage.getItem("username");
  const [sortingKey, setSortingKey] = useState("Newest");
  const [postResponse, setPostResponse] = useState([]);
  const pageIndexRef = useRef(0);

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
            console.log(res);
            return res.content;
          })
          .then((res) => updatePostData(res));
      } catch (e) {
        console.error("Error in home-component", e);
      } finally {
        console.log("Fetching posts completed");
        hideLoader();
      }
    },
    [sortingKey],
  );

  const memoizedGetAllPosts = useMemo(() => getAllPosts, [getAllPosts]);

  useEffect(() => {
    const initialise = async () => {
      let pageSize = 10;
      let sortBy = "postedOn";
      let isDescending = true;

      switch (sortingKey) {
        case "Newest":
          sortBy = "postedOn";
          isDescending = true;
          break;
        case "Oldest":
          sortBy = "postedOn";
          isDescending = false;
          break;
        case "Popular":
          sortBy = "numberOfLikes";
          isDescending = true;
          break;
        default:
          sortBy = "postedOn";
          isDescending = true;
      }
      console.log("Sorting Key - ", sortingKey);
      await memoizedGetAllPosts(
        pageIndexRef.current,
        pageSize,
        sortBy,
        isDescending,
      );
    };
    initialise();
  }, [sortingKey]);

  const onPageIndexChange = async (newIndex) => {
    pageIndexRef.current = newIndex;
    await memoizedGetAllPosts(pageIndexRef.current);
  };

  const listItems = postData.map((post, index, { length }) => {
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
        <Separator hidden={index + 1 === length} className="my-1" />
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
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="inline-flex gap-2">
              <ArrowDownAZ />
              Sorted By: {sortingKey}
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent>
            <DropdownMenuRadioGroup
              value={sortingKey}
              onValueChange={setSortingKey}
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
