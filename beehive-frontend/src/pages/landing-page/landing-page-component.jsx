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
            <div className="max-w-2xl mx-auto p-4">
                <h1 className="relative z-10 text-lg md:text-7xl  bg-clip-text text-transparent bg-gradient-to-b from-neutral-200 to-neutral-600  text-center font-sans font-bold">
                    Beehive
                </h1>
                <Separator className="my-5" />
                <p className="text-neutral-500 max-w-lg mx-auto mb-5 text-sm text-center relative z-10">
                    Connect With The Hive
                </p>
                <div className="grid gap-4">
                    <Button variant="outline" onClick={onRegisterClick} className="w-full z-10">Join today</Button>
                    <Button variant="ghost" onClick={onLoginClick} className="w-full z-10">Already have an account? Login</Button>
                </div>
            </div>
            <BackgroundBeams />
        </div>
    );
}
