import { EntryTabComponent } from "../root/entry-tab-component";
import { HeroComponent } from "./hero-component";

export function LandingPageComponent() {
  return (
    <div className="h-screen w-full flex justify-center">
      <div className="flex-initial w-[50%] m-auto hidden lg:block">
        <HeroComponent />
      </div>
      <div className="relative flex-initial w-[50%] flex items-center justify-center">
        <EntryTabComponent />
      </div>
    </div>
  );
}
