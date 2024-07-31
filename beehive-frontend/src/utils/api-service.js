import axios from "axios";

const api = axios.create({
  timeout: 5000,
  withCredentials: true,
});

/* api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token && !isJwtExpired(token)) {
      config.headers.Authorization = `Bearer ${token}`;
    } else if (token && isJwtExpired(token)) {
      onLogout("Token expired");
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
); */

const newAbortSignal = (timeoutMs) => {
  const abortController = new AbortController();
  setTimeout(() => abortController.abort(), timeoutMs || 0);

  return abortController.signal;
};

export const HttpGet = async (endpoint, parameters = {}, headers = {}) => {
  try {
    if (typeof endpoint == "string") {
      const result = await api.get(endpoint, {
        params: parameters,
        headers: headers,
        signal: newAbortSignal(5000),
      });
      return result;
    }
    throw new Error("Incorrect GET Request");
  } catch (error) {
    console.log("Axios GET Error:", error);
  }
};

export const HttpPost = async (endpoint, body = {}, headers = {}) => {
  try {
    if (typeof endpoint == "string") {
      const result = await api.post(endpoint, body, {
        headers: headers,
        signal: newAbortSignal(5000),
      });
      return result;
    }
    throw new Error("Incorrect POST Request");
  } catch (error) {
    console.log("Axios POST Error:", error);
  }
};

export const HttpDelete = async (endpoint, body = {}, headers = {}) => {
  try {
    if (typeof endpoint == "string") {
      const result = await api.delete(endpoint, {
        params: { ...body },
        headers: headers,
        signal: newAbortSignal(5000),
      });
      return result;
    }
    throw new Error("Incorrect DELETE Request");
  } catch (error) {
    console.log("Axios DELETE Error:", error);
  }
};
