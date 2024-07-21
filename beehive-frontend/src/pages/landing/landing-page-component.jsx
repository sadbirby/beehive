import { EntryTabComponent } from "../root/entry-tab-component";
import { HeroComponent } from "./hero-component";

export function LandingPageComponent() {
  return (
    <div className="flex h-screen w-full justify-center">
      <div className="m-auto hidden w-[50%] flex-initial lg:block">
        <HeroComponent />
      </div>
      <div className="relative flex w-[50%] flex-initial items-center justify-center">
        <EntryTabComponent />
      </div>
    </div>
  );
}
