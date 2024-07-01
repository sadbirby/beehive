import { jwtDecode } from "jwt-decode";

export const isJwtExpired = (token) => {
  const decodedToken = jwtDecode(token);
  console.log("Decoded Token", decodedToken);
  const currentDate = new Date();

  // JWT exp is in seconds
  if (decodedToken.exp * 1000 < currentDate.getTime()) {
    console.log("Token expired.");
    return true;
  } else {
    console.log("Valid token.");
    return false;
  }
};
