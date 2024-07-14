import { z } from "zod";

import {
  Button,
  Card,
  CardContent,
  CardFooter,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Input,
} from "@/components/ui/";
import {} from "@/components/ui/input";
import { useGlobalAppContext } from "@/context/app-context";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { fetchLoggedInUserDetails } from "../home/home-helper";
import { authenticate } from "./login-helper";

const formSchema = z
  .object({
    username: z
      .string()
      .min(4, { message: "Username shorter than 4 characters not accepted!" })
      .max(20, { message: "Username longer than 20 characters not accepted!" }),

    password: z
      .string()
      .min(8, { message: "Password shorter than 8 characters not accepted!" })
      .max(32, { message: "Password longer than 32 characters not accepted!" })
      .regex(
        new RegExp(
          "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$",
        ),
        {
          message:
            "Password must contain minimum 8 and maximum 32 characters, at least one uppercase letter, one lowercase letter, one number and one special character",
        },
      ),
  })
  .required();

export function LoginComponent() {
  const {
    loaderEnabled,
    loaderMessage,
    showLoader,
    hideLoader,
    updateOnlineStatus,
  } = useGlobalAppContext();

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  useEffect(() => {
    hideLoader();
  }, []);

  const onLoginClick = async (requestBody) => {
    try {
      showLoader();
      let username = requestBody.username;
      let password = requestBody.password;
      let token = await authenticate(username, password);

      if (token !== null) {
        localStorage.setItem("token", token);
        localStorage.setItem("isAuthenticated", true);
        localStorage.setItem("username", username);
        await fetchLoggedInUserDetails(username).then((res) =>
          localStorage.setItem("userData", JSON.stringify(res)),
        );
        // display toast and update page
        toast.success("Logged In");
        form.reset();
        updateOnlineStatus(true);
      }
    } catch (e) {
      toast.error("Incorrect Credentials");
      console.error("Error in login-component", e);
    } finally {
      hideLoader();
    }
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onLoginClick)}
        className="w-full space-y-8"
      >
        <Card>
          <CardContent className="space-y-2 pt-4">
            <FormField
              control={form.control}
              name="username"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Username</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <Input type="password" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter>
            {loaderEnabled ? (
              <Button disabled className="w-full text-base font-normal">
                <Loader2 className="mr-2 animate-spin" />
                {loaderMessage}
              </Button>
            ) : (
              <Button
                className="w-full text-base font-normal"
                size="lg"
                type="submit"
              >
                Log In
              </Button>
            )}
          </CardFooter>
        </Card>
      </form>
    </Form>
  );
}
