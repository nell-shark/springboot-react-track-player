import { BASE_URL } from '@data/constants';
import axios from 'axios';

export const axiosInstance = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-type': 'application/json',
  },
});
