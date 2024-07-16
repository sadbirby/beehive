import { EntryTabComponent } from "../root/entry-tab-component";
import { HeroComponent } from "./hero-component";

export function LandingPageComponent() {
  return (
    <div className="h-screen w-full lg:grid lg:grid-cols-2 lg:place-items-stretch">
      <div className="m-auto hidden justify-center lg:flex">
        <HeroComponent />
      </div>
      <div className="flex items-center justify-center py-12">
        <EntryTabComponent />
      </div>
    </div>
  );
}
