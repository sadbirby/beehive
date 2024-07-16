import { Separator } from "@/components/ui/separator";

export function HeroComponent() {
  return (
    <div className="mx-auto flex w-full flex-col justify-center gap-2">
      <h1 className="relative bg-primary bg-clip-text text-center text-lg font-bold text-primary md:text-7xl">
        Beehive
      </h1>
      <Separator className="bg-primary" />
      <div className="relative mx-auto max-w-lg rounded-md bg-primary p-2">
        <p className="text-center text-xl font-light text-primary-foreground">Connect With The Hive</p>
      </div>
    </div>
  );
}
