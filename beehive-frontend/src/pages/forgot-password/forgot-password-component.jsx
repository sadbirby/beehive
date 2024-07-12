import { Button } from "@/components/ui/button";
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";

export function ForgotPasswordComponent() {
  const { updateSelectedPage } = useGlobalAppContext();

  const onLoginClick = () => {
    updateSelectedPage(pages.PAGE_LOGIN);
  };

  return (
    <>
      <div className="flex items-center">
        <h1 className="text-lg font-semibold md:text-2xl">
          ForgotPasswordComponent
        </h1>
        <Button onClick={onLoginClick}>Back to login</Button>
      </div>
    </>
  );
}
