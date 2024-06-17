import { useEffect, useState } from "react";
import { fetchAllPosts, likeTweet, postReplyTweet, unlikeTweet } from "./home-helper";
import { useGlobalAppContext } from "@/context/app-context";
import { Card, CardContent, CardDescription, CardFooter, CardHeader } from '@/components/ui/card';
// import PopularPostsComponent from './popular-posts-component';
import { pages } from '@/constants/pages';
import { ArrowBigUp, MessageSquareIcon } from "lucide-react";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import { getRelativeDate } from "@/service/relative-date-service";

// import FeedComponent from './feed-component';

const HomeComponent = () => {

    const { selectedPage, userData, showLoader, hideLoader } = useGlobalAppContext();
    const [postList, setPostList] = useState([]);

    const getAllPosts = async () => {
        try {
            showLoader("fetching All Posts");
            setPostList(await fetchAllPosts(userData.username));
            console.log('GetAllPosts', postList);
            hideLoader();
        } catch (e) {
            hideLoader();
        }
    };

    useEffect(() => {
        console.log('INSIDE HOME.JSX USEEFFECT');
        const initialise = async () => {
            await getAllPosts();
        }
        initialise();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [selectedPage]);

    const listItems = postList.map((post) => {
        return (
            <div key={post.postId}>
                <Card className="px-0 py-2 border-none inline-flex w-full hover:bg-secondary/[40%]">
                    <div className='pl-4 pr-0 py-0 grid grid-rows-2 gap-0 place-items-start'>
                        <div>
                            <Avatar>
                                <AvatarFallback>{post.postedBy.substring(0, 2).toUpperCase()}</AvatarFallback>
                            </Avatar>
                        </div>
                    </div>
                    <div className='flex-initial w-full'>
                        <CardHeader className="py-0 px-4">
                            <div className="grid grid-cols-2 gap-0">
                                <div>
                                    <p className='text-sm'>@{post.postedBy}</p>
                                </div>
                                <div className='place-self-end'>
                                    <CardDescription className="text-right">
                                        {getRelativeDate(post.postedOn)}
                                    </CardDescription>
                                </div>

                            </div>
                        </CardHeader>
                        <CardContent className='cursor-pointer px-4 py-1'><p className='font-bold'>{post.postTitle}</p></CardContent>
                        <CardContent className='cursor-pointer px-4 py-1'><p className='font-thin'>{post.postBody}</p></CardContent>
                        <CardFooter className="px-4 py-0 grid grid-cols-2">
                            <div className='inline-flex gap-4'>
                                <div className='cursor-pointer border bg-secondary rounded-full inline-flex pl-2 pr-4 py-1 items-center gap-2'>
                                    <p className='text-center font-extralight text-xs'>{post.numberOfLikes}</p>
                                </div>
                                <div className='cursor-pointer border bg-secondary rounded-full inline-flex pl-2 pr-4 py-1 items-center gap-2'>
                                    <MessageSquareIcon />
                                    <p className='text-center font-extralight text-xs'>{post.numberOfReplies}</p>
                                </div>
                            </div>
                        </CardFooter>
                    </div>
                </Card >
            </div>)
    });

    return (
        <div className="grid grid-cols-1 gap-2">
            <Card className="px-0 py-4"></Card>
            <div className="grid grid-cols-1 gap-2">
                {listItems}
            </div>
        </div>
    )
}

export default HomeComponent;