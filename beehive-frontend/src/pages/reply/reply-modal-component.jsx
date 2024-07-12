import {
  Button,
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
  Input,
} from "@/components/ui";
import { useGlobalAppContext } from "@/context/app-context";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { z } from "zod";
import { replyToPost } from "./reply-helper";

const FormSchema = z.object({
  replyBody: z.string().min(1, {
    message: "Reply cannot be empty!",
  }),
});

export function ReplyModalComponent({
  open,
  onOpenChange,
  postId,
  onPageIndexChange,
}) {
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
        toast.success("Reply Added");
        form.reset();
        onPageIndexChange(0);
        onOpenChange(!open);
      }
    } catch (e) {
      toast.error("Cannot Reply To Post");
      console.error("Error in reply-area-component", e);
    } finally {
      hideLoader();
    }
  };

  return (
    <Dialog modal={true} open={open} onOpenChange={onOpenChange}>
      <DialogContent className="min-h-[60%] min-w-[40%] sm:max-w-[425px]">
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
                      className="h-32"
                      placeholder="Add A Reply"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <DialogFooter>
              {loaderEnabled ? (
                <Button
                  disabled
                  className="w-full rounded-full text-base font-normal"
                >
                  <Loader2 className="mr-2 animate-spin" />
                  {loaderMessage}
                </Button>
              ) : (
                <Button
                  className="w-full rounded-full text-base font-normal"
                  type="submit"
                >
                  Reply
                </Button>
              )}
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}
