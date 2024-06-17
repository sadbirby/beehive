import { GET_USER } from "@/constants/endpoints";
import { HttpGet } from "./api-service";

export const fetchLoggedInUserDetails = async () => {

    var credentials = "Bearer " + localStorage.getItem("token");
    var apiUrl = GET_USER + localStorage.getItem("loginId");
    var headers = {
        "Authorization": credentials,
    }
    var response = await HttpGet(apiUrl, {}, headers)

    return response.data.usersDto[0];
}