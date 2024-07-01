/* eslint-disable react/prop-types */
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Label } from "@/components/ui/label";
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetTrigger,
} from "@/components/ui/sheet";
import { Switch } from "@/components/ui/switch";
import { classnames } from "@/constants/classnames";
import { pages } from "@/constants/pages";
import { useGlobalAppContext } from "@/context/app-context";
import { CircleUser, Hexagon, Home, Menu, Users } from "lucide-react";
import { useEffect, useState } from "react";
import { toast } from "sonner";
import CreatePostComponent from "./create-post/create-post-component";
import HomeComponent from "./home/home-component";
import MyTweetsComponent from "./my-tweets/my-tweets-component";
import ReplyComponent from "./reply/reply-component";

const RootPostComponent = () => {
  const {
    selectedPage,
    userData,
    colorTheme,
    updateUserData,
    updateSelectedPage,
    showLoader,
    hideLoader,
    updateTheme,
  } = useGlobalAppContext();
  const [activeComponent, setActiveComponent] = useState(<HomeComponent />);

  useEffect(() => {
    const initialise = async () => {
      // theme change logic
      const root = window.document.documentElement;
      root.classList.remove("light", "dark");
      localStorage.setItem("color-theme", colorTheme);
      root.classList.add(colorTheme);

      // update user data
      if (userData !== undefined) {
        let user = JSON.parse(localStorage.getItem("userData"));
        updateUserData(user.data.users[0]);
      }

      // page change logic
      switch (selectedPage) {
        case pages.PAGE_HOME: {
          console.log("set home component");
          setActiveComponent(<HomeComponent />);
          break;
        }
        case pages.PAGE_REPLY: {
          console.log("set reply component");
          setActiveComponent(<ReplyComponent />);
          break;
        }
        case pages.PAGE_MY_TWEETS: {
          console.log("set mytweets component");
          setActiveComponent(<MyTweetsComponent />);
          break;
        }
        case pages.PAGE_CREATE_POST: {
          console.log("set create post component");
          setActiveComponent(<CreatePostComponent />);
          break;
        }
        default:
          setActiveComponent(<HomeComponent />);
      }
    };
    initialise();
  }, [colorTheme, selectedPage]);

  const activeTabStyle = (page) => {
    return page === selectedPage
      ? {
          full: classnames.NAV_ACTIVE,
          collapsed: classnames.NAV_COLLAPSED_ACTIVE,
        }
      : { full: classnames.NAV, collapsed: classnames.NAV_COLLAPSED };
  };

  const onLogout = (event) => {
    event.preventDefault();
    showLoader("Logging out");
    localStorage.clear();
    toast.info("Logged Out");
    updateSelectedPage(pages.PAGE_LOGIN);
    hideLoader();
  };

  return (
    <>
      <div className="grid min-h-screen w-full lg:grid-cols-[240px_1fr]">
        <div className="hidden bg-background lg:block">
          <div className="sticky left-0 top-0 flex h-full max-h-screen flex-col gap-2 border-r">
            <div className="flex h-14 items-center px-4 lg:h-[60px] lg:px-6">
              <div className="flex items-center gap-2 text-lg font-extrabold">
                <Hexagon className="h-6 w-6" />
                <span className="">Beehive</span>
              </div>
            </div>
            <div className="flex-1">
              <nav className="grid items-start px-2 text-sm font-medium lg:px-4">
                <a
                  className={activeTabStyle(pages.PAGE_HOME).full}
                  onClick={() => updateSelectedPage(pages.PAGE_HOME)}
                >
                  <Home className="h-4 w-4" />
                  Home
                </a>
                <a
                  className={activeTabStyle(pages.PAGE_MY_TWEETS).full}
                  onClick={() => updateSelectedPage(pages.PAGE_MY_TWEETS)}
                >
                  <Users className="h-4 w-4" />
                  Profile
                </a>
              </nav>
            </div>
            <div className="mt-auto p-4">
              <Button
                size="lg"
                className="w-full"
                onClick={() => updateSelectedPage(pages.PAGE_CREATE_POST)}
              >
                Create Post
              </Button>
            </div>
          </div>
        </div>

        <div className="flex flex-col">
          {/* Header starts */}
          <header className="sticky top-0 z-50 flex h-14 items-center gap-1 border-b bg-background px-4 lg:h-14 lg:px-6">
            {/* Sheet when in mobile viewport */}
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
                <nav className="grid gap-2 font-medium">
                  <SheetClose asChild>
                    <a className="flex items-center gap-2 pb-2 text-lg font-extrabold">
                      <Hexagon className="h-6 w-6" />
                      <span className="sr-only">Beehive</span>
                    </a>
                  </SheetClose>
                  <SheetClose asChild>
                    <a
                      className={activeTabStyle(pages.PAGE_HOME).collapsed}
                      onClick={() => updateSelectedPage(pages.PAGE_HOME)}
                    >
                      <Home className="h-5 w-5" />
                      Home
                    </a>
                  </SheetClose>
                  <SheetClose asChild>
                    <a
                      className={activeTabStyle(pages.PAGE_MY_TWEETS).collapsed}
                      onClick={() => updateSelectedPage(pages.PAGE_MY_TWEETS)}
                    >
                      <Users className="h-5 w-5" />
                      Profile
                    </a>
                  </SheetClose>
                </nav>
                <div className="mt-auto">
                  <SheetClose asChild>
                    <Button
                      size="lg"
                      className="w-full"
                      onClick={() => updateSelectedPage(pages.PAGE_CREATE_POST)}
                    >
                      Create Post
                    </Button>
                  </SheetClose>
                </div>
              </SheetContent>
            </Sheet>
            {/* Sheet ends */}
            <div className="w-full flex-1"></div>
            {/* User Menu Dropdown */}
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="primary" size="icon">
                  <CircleUser className="h-6 w-6" />
                  <span className="sr-only">Toggle user menu</span>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuLabel>{userData.username}</DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuItem>Settings</DropdownMenuItem>
                <DropdownMenuSeparator />
                <DropdownMenuItem>
                  <div className="flex items-center space-x-4">
                    <Label className="font-normal" htmlFor="dark-mode">
                      Dark Mode
                    </Label>
                    <Switch
                      id="dark-mode"
                      checked={colorTheme === "dark" ? true : false}
                      onClick={(e) => {
                        e.stopPropagation();
                        colorTheme === "dark"
                          ? updateTheme("light")
                          : updateTheme("dark");
                      }}
                    />
                  </div>
                </DropdownMenuItem>
                <DropdownMenuSeparator />
                <DropdownMenuItem onClick={onLogout}>Logout</DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </header>
          {/* Header ends */}
          <div className="flex flex-1 flex-col gap-4 p-3 lg:gap-6 lg:p-4">
            {activeComponent}
          </div>
        </div>
      </div>
    </>
  );
};

export default RootPostComponent;
