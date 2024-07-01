import { toast } from "sonner";

export const onLogout = (message) => {
  localStorage.clear();
  toast.info(message);
};
