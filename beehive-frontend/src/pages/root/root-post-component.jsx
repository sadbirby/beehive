import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
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
import { CircleUser, Home, Menu, PlusSquare, Users } from "lucide-react";
import { useEffect, useState } from "react";
import { Link, NavLink, Outlet, useNavigate } from "react-router-dom";
import { toast } from "sonner";

const RootPostComponent = () => {
  const {
    colorTheme,
    updateOnlineStatus,
    showLoader,
    hideLoader,
    updateTheme,
  } = useGlobalAppContext();
  const navigate = useNavigate();
  const currentUser = localStorage.getItem("username");
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    const root = window.document.documentElement;
    root.classList.remove("light", "dark");
    localStorage.setItem("color-theme", colorTheme);
    root.classList.add(colorTheme);
  }, [colorTheme]);

  const onLogout = (event) => {
    event.preventDefault();
    setIsOpen(false);
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
            className="shrink-0 border-none md:hidden"
          >
            <Menu className="h-5 w-5" />
            <span className="sr-only">Toggle navigation menu</span>
          </Button>
        </SheetTrigger>
        <SheetContent side="left" className="flex w-60 flex-col">
          <nav className="grid items-start gap-1 p-4">
            <SheetClose asChild>
              <SheetHeader>
                <SheetTitle>
                  <div className="pb-2 text-left text-lg font-extrabold">
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
        </SheetContent>
      </Sheet>
    );
  };

  const NavComponent = () => {
    return (
      <nav className="fixed left-0 hidden min-h-svh w-40 space-y-1 border-r p-4 md:block">
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
    );
  };

  const DropdownComponent = () => {
    return (
      <>
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

            <DropdownMenuItem onSelect={() => setIsOpen(true)}>
              Logout
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
        <AlertDialog open={isOpen} onOpenChange={setIsOpen}>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle>Logout</AlertDialogTitle>
              <AlertDialogDescription>
                Are you absolutely sure? This action cannot be undone.
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
              <AlertDialogCancel>Cancel</AlertDialogCancel>
              <AlertDialogAction onClick={onLogout}>Continue</AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      </>
    );
  };

  return (
    <div className="relative flex flex-col">
      <header className="fixed inset-x-0 top-0 z-50 flex h-14 items-center justify-between gap-1 border-b bg-background px-4">
        <div className="hidden items-center gap-2 md:block">
          <h1 className="text-3xl font-extrabold">Beehive</h1>
        </div>
        <SheetComponent />
        <div className="flex">
          <div className="">
            <Link to="/create">
              <Button variant="ghost" className="gap-x-2 font-normal">
                Create
                <PlusSquare />
              </Button>
            </Link>
          </div>
          <DropdownComponent />
        </div>
      </header>
      <div className="min-w-screen relative top-14 flex flex-1">
        <NavComponent />
        <div className="flex min-w-fit flex-1 flex-col p-4 md:ml-40 md:gap-6">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default RootPostComponent;
