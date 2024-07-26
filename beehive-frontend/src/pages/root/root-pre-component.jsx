import { LandingPageComponent } from "../landing/landing-page-component";

const RootPreComponent = () => {
  return (
    <div className="relative flex h-[40rem] w-full flex-col items-center justify-center antialiased">
      <LandingPageComponent />
    </div>
  );
};

export default RootPreComponent;
