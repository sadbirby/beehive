import {
  Button,
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuPortal,
  DropdownMenuSeparator,
  DropdownMenuSub,
  DropdownMenuSubContent,
  DropdownMenuSubTrigger,
  DropdownMenuTrigger,
  Sheet,
  SheetClose,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui";
import { classnames } from "@/constants/classnames";
import { useGlobalAppContext } from "@/context/app-context";
import { CircleUser, Hexagon, Home, Menu, Users } from "lucide-react";
import { useEffect } from "react";
import { Link, NavLink, Outlet, useNavigate } from "react-router-dom";
import { toast } from "sonner";

const RootPostComponent = () => {
  const {
    selectedPage,
    userData,
    colorTheme,
    updateUserData,
    updateSelectedPage,
    updateOnlineStatus,
    showLoader,
    hideLoader,
    updateTheme,
  } = useGlobalAppContext();
  const navigate = useNavigate();
  const currentUser = localStorage.getItem("username");

  useEffect(() => {
    console.log("rendered root-post-login-component");
    // theme change logic
    const root = window.document.documentElement;
    root.classList.remove("light", "dark");
    localStorage.setItem("color-theme", colorTheme);
    root.classList.add(colorTheme);
  }, [colorTheme]);

  const onLogout = (event) => {
    event.preventDefault();
    showLoader();
    localStorage.clear();
    updateOnlineStatus(false);
    navigate("/", { replace: true });
    hideLoader();
    toast.info("Logged Out");
  };

  const SheetComponent = () => {
    return (
      <Sheet>
        <SheetTrigger asChild>
          <Button
            variant="outline"
            size="icon"
            className="shrink-0 border-none lg:hidden"
          >
            <Menu className="h-5 w-5" />
            <span className="sr-only">Toggle navigation menu</span>
          </Button>
        </SheetTrigger>
        <SheetContent side="left" className="flex flex-col">
          <div className="flex-1">
            <nav className="grid items-start gap-2 p-1">
              <SheetClose asChild>
                <SheetHeader>
                  <SheetTitle>
                    <div className="flex items-center gap-2 pb-2 text-lg font-extrabold">
                      <Hexagon className="h-6 w-6" />
                      <h1 className="text-3xl font-extrabold">Beehive</h1>
                    </div>
                  </SheetTitle>
                  <SheetDescription />
                </SheetHeader>
              </SheetClose>
              <SheetClose asChild>
                <NavLink to="/home">
                  {({ isActive }) => {
                    return (
                      <div
                        className={
                          isActive
                            ? classnames.NAV_COLLAPSED_ACTIVE
                            : classnames.NAV_COLLAPSED
                        }
                      >
                        <Home className="h-5 w-5" />
                        Home
                      </div>
                    );
                  }}
                </NavLink>
              </SheetClose>
              <SheetClose asChild>
                <NavLink key={currentUser} to={`/${currentUser}/posts`}>
                  {({ isActive }) => {
                    return (
                      <div
                        className={
                          isActive
                            ? classnames.NAV_COLLAPSED_ACTIVE
                            : classnames.NAV_COLLAPSED
                        }
                      >
                        <Users className="h-5 w-5" />
                        Profile
                      </div>
                    );
                  }}
                </NavLink>
              </SheetClose>
            </nav>
          </div>
          <div className="mt-auto">
            <SheetClose asChild>
              <Link to="/create">
                <Button size="lg" className="w-full text-base font-normal">
                  Create Post
                </Button>
              </Link>
            </SheetClose>
          </div>
        </SheetContent>
      </Sheet>
    );
  };

  const DropdownComponent = () => {
    return (
      <DropdownMenu modal={false}>
        <DropdownMenuTrigger asChild>
          <Button variant="primary" className="hover:bg-secondary/[60%]">
            {currentUser}
            <CircleUser className="ml-2 h-6 w-6" />
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end">
          <DropdownMenuItem>Settings</DropdownMenuItem>
          <DropdownMenuSub>
            <DropdownMenuSubTrigger>Theme</DropdownMenuSubTrigger>
            <DropdownMenuPortal>
              <DropdownMenuSubContent>
                <DropdownMenuItem
                  disabled={colorTheme === "dark"}
                  onClick={() => updateTheme("dark")}
                >
                  Dark
                </DropdownMenuItem>
                <DropdownMenuItem
                  disabled={colorTheme === "light"}
                  onClick={() => updateTheme("light")}
                >
                  Light
                </DropdownMenuItem>
              </DropdownMenuSubContent>
            </DropdownMenuPortal>
          </DropdownMenuSub>
          <DropdownMenuSeparator />
          <DropdownMenuItem onClick={onLogout}>Logout</DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    );
  };

  return (
    <>
      <div className="grid min-h-screen w-full lg:grid-cols-[240px_1fr]">
        <div className="hidden bg-background lg:block">
          <div className="sticky left-0 top-0 flex h-full max-h-screen flex-col gap-2 border-r">
            <div className="flex h-14 items-center px-4 lg:h-[60px] lg:px-6">
              <div className="flex items-center gap-2">
                <Hexagon className="h-6 w-6" />
                <h1 className="text-3xl font-extrabold">Beehive</h1>
              </div>
            </div>
            <div className="flex-1">
              <nav className="grid items-start gap-2 px-2 py-1">
                <NavLink
                  to="/home"
                  className={({ isActive }) => {
                    return isActive ? classnames.NAV_ACTIVE : classnames.NAV;
                  }}
                >
                  <Home className="h-4 w-4" />
                  Home
                </NavLink>
                <NavLink
                  key={currentUser}
                  to={`/${currentUser}/posts`}
                  className={({ isActive }) => {
                    return isActive ? classnames.NAV_ACTIVE : classnames.NAV;
                  }}
                >
                  <Users className="h-4 w-4" />
                  Profile
                </NavLink>
              </nav>
            </div>
            <div className="mt-auto p-4">
              <Link to="/create">
                <Button size="lg" className="w-full text-base font-normal">
                  Create Post
                </Button>
              </Link>
            </div>
          </div>
        </div>

        <div className="flex flex-col">
          {/* Header starts */}
          <header className="sticky top-0 z-50 flex h-14 items-center gap-1 border-b bg-background px-4 lg:h-14 lg:px-6">
            {/* Sheet when in mobile viewport */}
            <SheetComponent />
            {/* Sheet ends */}
            <div className="w-full flex-1"></div>
            {/* User Menu Dropdown */}
            <DropdownComponent />
          </header>
          {/* Header ends */}
          <div className="flex flex-1 flex-col gap-4 bg-secondary/[15%] p-3 lg:gap-6 lg:p-4">
            {/* {activeComponent} */}
            <Outlet />
          </div>
        </div>
      </div>
    </>
  );
};

export default RootPostComponent;
