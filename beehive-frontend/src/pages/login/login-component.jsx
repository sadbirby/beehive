/* eslint-disable react/prop-types */
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { pages } from "@/constants/pages"
import { useGlobalAppContext } from "@/context/app-context"
import { useState } from "react"
import { authenticate } from "./login-helper"
import { toast } from "@/components/ui/use-toast"

export function LoginComponent() {

    const { showLoader, hideLoader, updateSelectedPage } = useGlobalAppContext();

    const [requestBody, setRequestBody] = useState({
        username: "",
        password: "",
    });

    const [errorMessage, setErrorMessage] = useState("");

    const handleChange = (prop) => (event) => {
        setErrorMessage("")
        setRequestBody({ ...requestBody, [prop]: event.target.value });
    };

    const onForgotPasswordClick = () => updateSelectedPage(pages.PAGE_FORGOT_PASSWORD);
    const onRegisterClick = () => updateSelectedPage(pages.PAGE_REGISTER);

    const onLoginClick = async () => {
        try {
            showLoader("Logging in");
            var token = await authenticate(requestBody.username, requestBody.password);
            toast({
                description: "Logged In",
            })
            localStorage.setItem("isAuthenticated", true);
            localStorage.setItem("token", token);
            localStorage.setItem("username", requestBody.username);
            updateSelectedPage(pages.PAGE_HOME);
            hideLoader();
        } catch (e) {
            setErrorMessage("Incorrect Credentials");
            toast({
                variant: "destructive",
                description: "Incorrect Credentials",
            })
            hideLoader();
            console.log(errorMessage);
        }
    }

    return (
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
                            onChange={handleChange('username')}
                            required
                        />
                    </div>
                    <div className="grid gap-2">
                        <div className="flex items-center">
                            <Label htmlFor="password">Password</Label>
                            <a
                                className="cursor-pointer ml-auto inline-block text-sm underline"
                                onClick={onForgotPasswordClick}>
                                Forgot password?
                            </a>
                        </div>
                        <Input
                            id="password"
                            type="password"
                            onChange={handleChange('password')}
                            required />
                    </div>
                    <Button
                        type="submit"
                        className="w-full"
                        onClick={onLoginClick}>
                        Login
                    </Button>
                </div>
                <div className="mt-4 text-center text-sm">
                    Don&apos;t have an account?{" "}
                    <a
                        className="cursor-pointer underline"
                        onClick={onRegisterClick}>
                        Sign up
                    </a>
                </div>
            </CardContent>
        </Card>
    )
}
