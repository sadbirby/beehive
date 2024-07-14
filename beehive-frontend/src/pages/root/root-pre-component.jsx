import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";
import { useEffect, useState } from "react";
import { HeroHighlight } from "../../components/ui/HeroHighlight";
import { ForgotPasswordComponent } from "../forgot-password/forgot-password-component";
import { LandingPageComponent } from "../landing/landing-page-component";

const RootPreComponent = () => {
  const { selectedPage } = useGlobalAppContext();
  const [activeComponent, setActiveComponent] = useState(
    <LandingPageComponent />,
  );

  useEffect(() => {
    switch (selectedPage) {
      case pages.PAGE_LANDING: {
        setActiveComponent(<LandingPageComponent />);
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
    <HeroHighlight className="w-full">
      <div className="relative flex h-[40rem] w-full flex-col items-center justify-center antialiased">
        {activeComponent}
      </div>
    </HeroHighlight>
  );
};

export default RootPreComponent;
