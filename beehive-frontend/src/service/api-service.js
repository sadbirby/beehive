import axios from 'axios';

const api = axios.create({
    timeout: 10000,
});

export const HttpGet = async (endpoint, parameters = {}, headers = {}) => {
    var result = '';
    try {
        if (typeof endpoint == 'string') {
            result = await api.get(endpoint, {
                params: parameters,
                headers: headers
            });
        }
    } catch (error) {
        console.log(error);
        throw JSON.parse(error.request._response).message.data.error.description;
    }
    console.log('Axios Result', result);
    return result;
};

export const HttpPost = async (endpoint, body = {}, headers = {}) => {
    try {
        if (typeof endpoint == 'string') {
            const result = await api.post(endpoint, body, {
                headers: headers
            });
            return result;
        }
        throw new Error('Incorrect request');
    } catch (error) {
        console.log('error', error);
        throw error;
    }
};

export const HttpDelete = async (endpoint, body = {}, headers = {}) => {
    try {
        if (typeof endpoint == 'string') {
            const result = await api.delete(endpoint, {
                params: { ...body },
                headers: headers
            });
            return result;
        }
        throw new Error('Incorrect request');
    } catch (error) {
        console.log('error', error);
        throw error;
    }
}
