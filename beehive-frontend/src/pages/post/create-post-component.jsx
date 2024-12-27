import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import {
  Button,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Input,
  LoadingSpinner,
  Textarea,
} from "@/components/ui";
import { useGlobalAppContext } from "@/context/app-context";
import { toast } from "sonner";
import { createPost } from "./create-post-helper";

const formSchema = z
  .object({
    postTitle: z
      .string()
      .min(1, { message: "Title cannot be empty!" })
      .max(255, { message: "Title cannot exceed 255 characters!" }),

    postBody: z.string().min(1, { message: "Body cannot be empty!" }),
  })
  .required();

const CreatePostComponent = () => {
  const { loaderEnabled, loaderMessage, showLoader, hideLoader } =
    useGlobalAppContext();

  const username = localStorage.getItem("username");

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      postTitle: "",
      postBody: "",
    },
  });

  const onSubmit = async (post) => {
    try {
      showLoader("Creating Post...");
      const response = await createPost(username, post);
      if (response.statusMessage === "SUCCESS") {
        toast.success("Post Created");
        form.reset();
      }
    } catch (e) {
      toast.error("Cannot Create Post");
      console.error("Error in create-post-component", e);
    } finally {
      hideLoader();
    }
  };

  return loaderEnabled ? (
    <div className="mt-8 flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
      <div>
        <h4>{loaderMessage}</h4>
      </div>
    </div>
  ) : (
    <div className="flex w-full max-w-5xl justify-center self-center p-4">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="w-[80%] space-y-8"
        >
          <FormField
            control={form.control}
            name="postTitle"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Title</FormLabel>
                <FormControl>
                  <Input placeholder="Title" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="postBody"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Body</FormLabel>
                <FormControl>
                  <Textarea placeholder="Body" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <Button size="lg" type="submit">
            Post
          </Button>
        </form>
      </Form>
    </div>
  );
};

export default CreatePostComponent;
