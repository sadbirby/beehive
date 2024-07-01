import { BackgroundBeams } from "@/components/ui/background-beams";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";

export function LandingPageComponent() {
  const { updateSelectedPage } = useGlobalAppContext();

  const onLoginClick = () => updateSelectedPage(pages.PAGE_LOGIN);
  const onRegisterClick = () => updateSelectedPage(pages.PAGE_REGISTER);

  return (
    <div>
      <div className="mx-auto max-w-2xl p-4">
        <h1 className="relative z-10 bg-gradient-to-b from-neutral-200 to-neutral-600 bg-clip-text text-center font-sans text-lg font-bold text-transparent md:text-7xl">
          Beehive
        </h1>
        <Separator className="my-5" />
        <p className="relative z-10 mx-auto mb-5 max-w-lg text-center text-sm text-neutral-500">
          Connect With The Hive
        </p>
        <div className="grid gap-4">
          <Button
            variant="outline"
            onClick={onRegisterClick}
            className="z-10 w-full"
          >
            Join today
          </Button>
          <Button
            variant="ghost"
            onClick={onLoginClick}
            className="z-10 w-full"
          >
            Already have an account? Login
          </Button>
        </div>
      </div>
      <BackgroundBeams />
    </div>
  );
}
