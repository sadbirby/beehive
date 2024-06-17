/* eslint-disable react/prop-types */
import {
    CircleUser,
    Home,
    Menu,
    Moon,
    Package2,
    Sun,
    Users,
} from "lucide-react"
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import { pages } from "@/constants/pages";
import HomeComponent from "./home/home-component";
import MyTweetsComponent from "./my-tweets/my-tweets-component";
import { useGlobalAppContext } from "@/context/app-context";
import { useEffect, useState } from "react"
import { classnames } from "@/constants/classnames"
import { fetchLoggedInUserDetails } from "./home/home-helper"
import { toast } from "@/components/ui/use-toast"

const RootPostComponent = () => {

    const { selectedPage, userData, colorTheme, updateSelectedPage, showLoader, hideLoader, updateUserData, updateTheme } = useGlobalAppContext();
    const [activeComponent, setActiveComponent] = useState(<HomeComponent />);

    useEffect(() => {
        console.log('INSIDE ROOT-POST.JSX USEEFFECT', userData);
        const initialise = async () => {
            const root = window.document.documentElement;
            root.classList.remove("light", "dark");
            localStorage.setItem("color-theme", colorTheme);
            root.classList.add(colorTheme);
            if (!userData.username) {
                try {
                    updateUserData((await fetchLoggedInUserDetails()).data.users[0]);
                } catch (err) {
                    console.log(err);
                }
            }
        };
        initialise();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [colorTheme]);

    const activeTabStyle = (page) => {
        return page === selectedPage ?
            { full: classnames.NAV_ACTIVE, collapsed: classnames.NAV_COLLAPSED_ACTIVE } : { full: classnames.NAV, collapsed: classnames.NAV_COLLAPSED };
    }

    const onLogout = (event) => {
        showLoader("Logging out");
        event.preventDefault();
        localStorage.clear();
        toast({
            description: "Logged Out",
        })
        updateSelectedPage(pages.PAGE_LOGIN);
        hideLoader();
    };

    const onTabChange = (page) => (event) => {
        event.preventDefault();
        showLoader("Updating")
        updateSelectedPage(page);
        switch (page) {
            case pages.PAGE_HOME: {
                setActiveComponent(<HomeComponent />);
                break;
            }
            case pages.PAGE_MY_TWEETS: {
                setActiveComponent(<MyTweetsComponent />);
                break;
            }
            default:
                setActiveComponent(<HomeComponent />);
        }
        hideLoader();
    }

    return (<>
        <div className="grid min-h-screen w-full md:grid-cols-[240px_1fr]">
            <div className="hidden bg-muted/40 md:block">
                <div className="sticky top-0 left-0 flex h-full max-h-screen flex-col gap-2">
                    <div className="flex h-14 items-center border-b px-4 lg:h-[60px] lg:px-6">
                        <div className="flex items-center gap-2 font-semibold">
                            <Package2 className="h-6 w-6" />
                            <span className="">Beehive</span>
                        </div>
                    </div>
                    <div className="flex-1">
                        <nav className="grid items-start px-2 text-sm font-medium lg:px-4">
                            <a
                                className={activeTabStyle(pages.PAGE_HOME).full}
                                onClick={onTabChange(pages.PAGE_HOME)}
                            >
                                <Home className="h-4 w-4" />
                                Home
                            </a>
                            <a
                                className={activeTabStyle(pages.PAGE_MY_TWEETS).full}
                                onClick={onTabChange(pages.PAGE_MY_TWEETS)}
                            >
                                <Users className="h-4 w-4" />
                                Profile
                            </a>
                        </nav>
                    </div>
                    <div className="mt-auto p-4">
                        <Card x-chunk="dashboard-02-chunk-0">
                            <CardHeader className="p-2 pt-0 md:p-4">
                                <CardTitle>Upgrade to Pro</CardTitle>
                                <CardDescription>
                                    Unlock all features and get unlimited access to our support
                                    team.
                                </CardDescription>
                            </CardHeader>
                            <CardContent className="p-2 pt-0 md:p-4 md:pt-0">
                                <Button size="sm" className="w-full">
                                    Upgrade
                                </Button>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </div>

            <div className="flex flex-col">
                <header className="sticky top-0 z-10 backdrop-blur flex h-14 items-center gap-4 border-b bg-muted/40 px-4 lg:h-[60px] lg:px-6">
                    <Sheet>
                        <SheetTrigger asChild>
                            <Button
                                variant="outline"
                                size="icon"
                                className="shrink-0 md:hidden"
                            >
                                <Menu className="h-5 w-5" />
                                <span className="sr-only">Toggle navigation menu</span>
                            </Button>
                        </SheetTrigger>
                        <SheetContent side="left" className="flex flex-col">
                            <nav className="grid gap-2 text-lg font-medium">
                                <a
                                    className="flex items-center gap-2 text-lg font-semibold"
                                >
                                    <Package2 className="h-6 w-6" />
                                    <span className="sr-only">Beehive</span>
                                </a>
                                <a
                                    className={activeTabStyle(pages.PAGE_HOME).collapsed}
                                    onClick={onTabChange(pages.PAGE_HOME)}
                                >
                                    <Home className="h-5 w-5" />
                                    Home
                                </a>
                                <a
                                    className={activeTabStyle(pages.PAGE_MY_TWEETS).collapsed}
                                    onClick={onTabChange(pages.PAGE_MY_TWEETS)}
                                >
                                    <Users className="h-5 w-5" />
                                    Profile
                                </a>
                            </nav>
                            <div className="mt-auto">
                                <Card>
                                    <CardHeader>
                                        <CardTitle>Upgrade to Pro</CardTitle>
                                        <CardDescription>
                                            Unlock all features and get unlimited access to our
                                            support team.
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <Button size="sm" className="w-full">
                                            Upgrade
                                        </Button>
                                    </CardContent>
                                </Card>
                            </div>
                        </SheetContent>
                    </Sheet>
                    <div className="w-full flex-1">
                    </div>
                    <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                            <Button variant="secondary" size="icon" className="rounded-full">
                                <Sun className="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0" />
                                <Moon className="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100" />
                                <span className="sr-only">Toggle theme</span>
                            </Button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="end">
                            <DropdownMenuItem onClick={() => updateTheme("light")}>
                                Light
                            </DropdownMenuItem>
                            <DropdownMenuItem onClick={() => updateTheme("dark")}>
                                Dark
                            </DropdownMenuItem>
                        </DropdownMenuContent>
                    </DropdownMenu>
                    <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                            <Button variant="secondary" size="icon" className="rounded-full">
                                <CircleUser className="h-5 w-5" />
                                <span className="sr-only">Toggle user menu</span>
                            </Button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="end">
                            <DropdownMenuLabel>{userData.username}</DropdownMenuLabel>
                            <DropdownMenuSeparator />
                            <DropdownMenuItem>Settings</DropdownMenuItem>
                            <DropdownMenuSeparator />
                            <DropdownMenuItem className="cursor-pointer" onClick={onLogout}>Logout</DropdownMenuItem>
                        </DropdownMenuContent>
                    </DropdownMenu>
                </header>

                <main className="flex flex-1 flex-col gap-4 p-4 lg:gap-6 lg:p-6">
                    {activeComponent}
                </main>
            </div>
        </div>
    </>);
}

export default RootPostComponent;