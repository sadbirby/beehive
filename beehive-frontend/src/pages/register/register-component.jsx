/* eslint-disable react/prop-types */
import { z } from "zod";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useGlobalAppContext } from "@/context/app-context";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { checkIfUserExists, register } from "./register-helper";

const formSchema = z
  .object({
    username: z
      .string()
      .min(4, { message: "Username shorter than 4 characters not accepted!" })
      .max(20, { message: "Username longer than 20 characters not accepted!" }),

    email: z.string().email({ message: "Invalid email address" }),

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

    confirmPassword: z
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
  .required()
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match!",
    path: ["confirmPassword"],
  })
  .refine(
    async (data) => {
      const res = await checkIfUserExists(data.username);
      return !res;
    },
    {
      message: "Username already exists!",
      path: ["username"],
    },
  );

export function RegisterComponent() {
  const {
    loaderEnabled,
    loaderMessage,
    showLoader,
    hideLoader,
    updateSelectedPage,
  } = useGlobalAppContext();

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
    },
    mode: "onSubmit",
    reValidateMode: "onSubmit",
  });

  const handleSubmit = async (requestBody) => {
    try {
      showLoader("Adding new user");
      let username = requestBody.username;
      let email = requestBody.email;
      let password = requestBody.password;
      const x = await register(username, email, password);
      if (x.statusMessage === "SUCCESS") {
        // display toast and reset form
        toast.success("Account Created");
        form.reset();
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
        onSubmit={form.handleSubmit(handleSubmit)}
        className="w-full space-y-8"
      >
        <Card>
          <CardHeader>
            <CardTitle className="font-light">Create A New Account</CardTitle>
          </CardHeader>
          <CardContent className="space-y-2">
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
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <Input type="email" {...field} />
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
            <FormField
              control={form.control}
              name="confirmPassword"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Confirm Password</FormLabel>
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
                size="lg"
                type="submit"
              >
                Sign Up
              </Button>
            )}
          </CardFooter>
        </Card>
      </form>
    </Form>
  );
}
