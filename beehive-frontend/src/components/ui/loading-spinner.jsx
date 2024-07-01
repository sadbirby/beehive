import { cn } from "@/lib/utils";
import * as React from "react";

const spinnerVariants =
  "h-8 w-8 animate-spin rounded-full border-4 border-t-4 border-primary/[25%] border-t-primary";

const LoadingSpinner = React.forwardRef((props, ref) => {
  const { className, ...rest } = props;
  return <div ref={ref} className={cn(spinnerVariants, className)} {...rest} />;
});

LoadingSpinner.displayName = "LoadingSpinner";

export { LoadingSpinner };
