import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { LoadingSpinner } from "@/components/ui/loading-spinner";
import { useGlobalAppContext } from "@/context/app-context";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { z } from "zod";
import { replyToPost } from "./reply-helper";

const FormSchema = z.object({
  replyBody: z.string().min(1, {
    message: "Reply cannot be empty!",
  }),
});

export const ReplyAreaComponent = ({ open, onOpenChange, postId, onPageIndexChange }) => {
  const { loaderEnabled, loaderMessage, userData, showLoader, hideLoader } =
    useGlobalAppContext();

  const form = useForm({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      replyBody: "",
    },
  });

  const onSubmit = async (data) => {
    try {
      showLoader("Replying To Post...");
      const response = await replyToPost(
        postId,
        data.replyBody,
        userData.username,
      );
      if (response.statusMessage === "SUCCESS") {
        toast.success("Replied To Post.");
        form.reset();
        onPageIndexChange(0);
        onOpenChange(!open);
      }
    } catch (e) {
      toast.error("Cannot Reply To Post. Please Try Again.");
      console.error("Error in reply-area-component", e);
    } finally {
      hideLoader();
    }
  };

  return loaderEnabled ? (
    <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
      <div>
        <h4 className="text-sm font-extralight">{loaderMessage}</h4>
      </div>
    </div>
  ) : (
    <Dialog modal={true} open={open} onOpenChange={onOpenChange}>
      {/* <DialogTrigger asChild>
        <Button variant="outline">Edit Profile</Button>
      </DialogTrigger> */}
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Reply To This Post</DialogTitle>
          <DialogDescription>
            Click on reply when you're done.
          </DialogDescription>
        </DialogHeader>
        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(onSubmit)}
            className="w-full space-y-6"
          >
            <FormField
              control={form.control}
              name="replyBody"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <Input
                      className="h-16"
                      placeholder="Add A Reply"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <DialogFooter>
              <Button type="submit">Reply</Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};
