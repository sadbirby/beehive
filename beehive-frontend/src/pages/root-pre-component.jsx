/* eslint-disable react/prop-types */
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";
import { useEffect, useState } from "react";
import ForgotPasswordComponent from "./forgot-password/forgot-password-component";
import { LandingPageComponent } from "./landing-page/landing-page-component";
import { LoginComponent } from "./login/login-component";
import RegisterComponent from "./register/register-component";

const RootPreComponent = () => {
  const { selectedPage } = useGlobalAppContext();
  const [activeComponent, setActiveComponent] = useState(
    <LandingPageComponent />,
  );

  useEffect(() => {
    console.log("INSIDE ROOT-PRE.JSX USEEFFECT");
    switch (selectedPage) {
      case pages.PAGE_LANDING: {
        setActiveComponent(<LandingPageComponent />);
        break;
      }
      case pages.PAGE_LOGIN: {
        setActiveComponent(<LoginComponent />);
        break;
      }
      case pages.PAGE_REGISTER: {
        setActiveComponent(<RegisterComponent />);
        break;
      }
      case pages.PAGE_FORGOT_PASSWORD: {
        setActiveComponent(<ForgotPasswordComponent />);
        break;
      }
      default:
        setActiveComponent(<LandingPageComponent />);
    }
  }, [selectedPage]);

  return (
    <div className="relative flex h-[40rem] w-full flex-col items-center justify-center antialiased">
      {activeComponent}
    </div>
  );
};

export default RootPreComponent;
