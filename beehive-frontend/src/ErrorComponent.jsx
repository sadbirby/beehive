import { Link } from "react-router-dom";
import { Button } from "./components/ui/button";
import { pages } from "./constants/pages";
import { useGlobalAppContext } from "./context/app-context";

export function ErrorComponent() {
  const { updateSelectedPage } = useGlobalAppContext();

  const onBackBtnClick = () => {
    updateSelectedPage(pages.PAGE_HOME);
  };

  return (
    <div className="flex min-h-[100vh] flex-col items-center justify-center space-y-4">
      <div className="space-y-2 text-center">
        <h1 className="text-8xl font-bold tracking-tighter sm:text-9xl">404</h1>
        <h1 className="text-4xl font-bold tracking-tighter sm:text-6xl">
          Page not found
        </h1>
        <p className="max-w-[600px] md:text-xl/relaxed">
          Sorry, we couldn&apos;t find the page you&apos;re looking for.
        </p>
      </div>
      <Link to="/">
        <Button
          onClick={onBackBtnClick}
          className="w-full rounded-full text-base font-normal"
        >
          Go Back
        </Button>
      </Link>
    </div>
  );
}
