import { Separator } from "@/components/ui/separator";
import { motion } from "framer-motion";
import { Highlight } from "../../components/ui/HeroHighlight";

export function HeroComponent() {
  return (
    <div className="mx-auto flex w-full flex-col justify-center gap-2">
      <motion.h1
        initial={{
          opacity: 0,
          y: 20,
        }}
        animate={{
          opacity: 1,
          y: [20, -5, 0],
        }}
        transition={{
          duration: 0.5,
          ease: [0.4, 0.0, 0.2, 1],
        }}
        className="relative bg-gradient-to-b from-neutral-200 to-neutral-600 bg-clip-text text-center font-sans text-lg font-bold text-transparent md:text-7xl"
      >
        Beehive
      </motion.h1>
      <Separator />
      <Highlight className="relative mx-auto max-w-lg rounded-full p-2 text-center text-xl font-light text-neutral-500 dark:text-white">
        Connect With The Hive
      </Highlight>
    </div>
  );
}
