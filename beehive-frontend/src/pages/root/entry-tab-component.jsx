import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { classnames } from "@/constants/classnames";
import { LoginComponent } from "../login/login-component";
import { RegisterComponent } from "../register/register-component";

export function EntryTabComponent() {
  return (
    <div className="lg:absolute top-[5%] right-[5%] lg:w-[70%] w-full">
      <Tabs defaultValue="login" className="h-auto w-full">
        <TabsList className="grid w-full grid-cols-2 rounded-md bg-secondary/[60%]">
          <TabsTrigger className={classnames.LANDING} value="login">
            Log In
          </TabsTrigger>
          <TabsTrigger className={classnames.LANDING} value="register">
            Sign Up
          </TabsTrigger>
        </TabsList>
        <TabsContent value="login">
          <LoginComponent />
        </TabsContent>
        <TabsContent value="register">
          <RegisterComponent />
        </TabsContent>
      </Tabs>
    </div>
  );
}
