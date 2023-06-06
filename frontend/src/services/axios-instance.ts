import {BASE_URL} from '@data/constants';
import axios from 'axios';

export const axiosInstance = axios.create({
    baseURL: BASE_URL,
    withCredentials: true,
    headers: {
        'Content-type': 'application/json'
    },
});
