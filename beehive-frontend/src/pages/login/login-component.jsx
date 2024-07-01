/* eslint-disable react/prop-types */
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { LoadingSpinner } from "@/components/ui/loading-spinner";
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";
import { useState } from "react";
import { toast } from "sonner";
import { fetchLoggedInUserDetails } from "../home/home-helper";
import { authenticate } from "./login-helper";

export function LoginComponent() {
  const {
    loaderEnabled,
    loaderMessage,
    showLoader,
    hideLoader,
    updateSelectedPage,
  } = useGlobalAppContext();

  const [requestBody, setRequestBody] = useState({
    username: "",
    password: "",
  });

  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (prop) => (event) => {
    setErrorMessage("");
    setRequestBody({ ...requestBody, [prop]: event.target.value });
  };

  const onForgotPasswordClick = () => {
    updateSelectedPage(pages.PAGE_FORGOT_PASSWORD);
  };

  const onRegisterClick = () => {
    updateSelectedPage(pages.PAGE_REGISTER);
  };

  const onLoginClick = async () => {
    try {
      showLoader("Logging in");
      let username = requestBody.username;
      let password = requestBody.password;
      let token = await authenticate(username, password);

      if (token !== null) {
        localStorage.setItem("token", token);
        localStorage.setItem("isAuthenticated", true);
        localStorage.setItem("username", username);
        let userData = await fetchLoggedInUserDetails(username);
        localStorage.setItem("userData", JSON.stringify(userData));
        // display toast and update page
        toast.success("Logged In");
        updateSelectedPage(pages.PAGE_HOME);
      }
    } catch (e) {
      setErrorMessage("Incorrect Credentials");
      toast.error("Incorrect Credentials");
      console.error("Error in login-component", e);
    } finally {
      hideLoader();
    }
  };

  return loaderEnabled ? (
    <div className="flex w-full flex-grow flex-col items-center justify-center gap-4">
      <LoadingSpinner />
      <div>
        <h4 className="text-sm font-extralight">{loaderMessage}</h4>
      </div>
    </div>
  ) : (
    <Card className="mx-auto max-w-sm">
      <CardHeader>
        <CardTitle className="text-2xl">Login</CardTitle>
        <CardDescription>
          Enter your username below to login to your account
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="grid gap-4">
          <div className="grid gap-2">
            <Label htmlFor="username">Username</Label>
            <Input
              id="username"
              type="text"
              onChange={handleChange("username")}
              required
            />
          </div>
          <div className="grid gap-2">
            <div className="flex items-center">
              <Label htmlFor="password">Password</Label>
              <a
                className="ml-auto inline-block cursor-pointer text-sm underline"
                onClick={onForgotPasswordClick}
              >
                Forgot password?
              </a>
            </div>
            <Input
              id="password"
              type="password"
              onChange={handleChange("password")}
              required
            />
          </div>
          <Button type="submit" className="w-full" onClick={onLoginClick}>
            Login
          </Button>
        </div>
        <div className="mt-4 text-center text-sm">
          Don&apos;t have an account?{" "}
          <a className="cursor-pointer underline" onClick={onRegisterClick}>
            Sign up
          </a>
        </div>
      </CardContent>
    </Card>
  );
}
