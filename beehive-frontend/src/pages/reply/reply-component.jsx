/* eslint-disable react/prop-types */
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
} from "@/components/ui/card";
import { LoadingSpinner } from "@/components/ui/loading-spinner";
import { Separator } from "@/components/ui/separator";
import { useGlobalAppContext } from "@/context/app-context";
import { getRelativeDate } from "@/utils/get-relative-date";
import {
  ChevronLeftIcon,
  ChevronRightIcon,
  ChevronsLeftIcon,
  ChevronsRightIcon,
} from "lucide-react";
import { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { PostCardComponent } from "../post/post-card-component";
import { fetchAllReplies } from "./reply-helper";
import { ReplyModalComponent } from "./reply-modal-component";

export function ReplyComponent() {
  const { loaderEnabled, loaderMessage, selectedPost, showLoader, hideLoader } =
    useGlobalAppContext();
  const [openModal, setOpenModal] = useState(false);
  const [replyResponse, setReplyResponse] = useState({});
  const pageIndexRef = useRef(0);
  const inputRef = useRef(1);

  const { postId } = useParams();
  console.info("Post ID: ", postId);

  useEffect(() => {
    const initialise = async () => {
      await getAllReplies(pageIndexRef.current);
    };
    initialise();
  }, []);

  const getAllReplies = async (pageNumber) => {
    try {
      showLoader("Fetching Replies...");
      await fetchAllReplies(selectedPost.postId, pageNumber).then((res) =>
        setReplyResponse(res),
      );
    } catch (e) {
      console.error("Error in reply-component");
    } finally {
      hideLoader();
    }
  };

  const onPageIndexChange = async (newIndex) => {
    pageIndexRef.current = newIndex;
    await getAllReplies(newIndex);
  };

  const listItems = replyResponse.content?.map((reply, index, { length }) => {
    return (
      <div key={reply.replyId}>
        <Card className="inline-flex w-full border-none px-0 py-4 shadow-none">
          <div className="grid grid-rows-2 place-items-start gap-0 py-0 pl-4 pr-0">
            <div>
              <Avatar className="rounded-none border-2">
                <AvatarFallback className="rounded-none bg-secondary/[50%] font-mono">
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
          </div>
        </Card>
        <Separator className="" hidden={index + 1 === length} />
      </div>
    );
  });

  return loaderEnabled ? (
    <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
      <div>
        <h4>{loaderMessage}</h4>
      </div>
    </div>
  ) : (
    <div className="grid grid-cols-1 gap-2">
      <div className="grid grid-cols-1 gap-3">
        <PostCardComponent
          post={selectedPost}
          openModal={openModal}
          setOpenModal={setOpenModal}
        />
        <ReplyModalComponent
          open={openModal}
          onOpenChange={setOpenModal}
          postId={selectedPost.postId}
          onPageIndexChange={onPageIndexChange}
        />
        {replyResponse.totalElements > 0 ? (
          <>
            <div className="grid grid-cols-1 gap-0 rounded-2xl border bg-background p-0 shadow">
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
}
