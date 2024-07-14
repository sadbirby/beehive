/* eslint-disable no-undef */
import react from "@vitejs/plugin-react-swc";
import path from "path";
import { defineConfig } from "vite";
import Inspect from "vite-plugin-inspect";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [Inspect(), react()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
