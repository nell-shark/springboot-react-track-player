import { axiosInstance } from '@services/axiosInstance';

import { User } from '../typings/user';

class UserService {
  public getUserInfo() {
    return axiosInstance.get<User>('/api/v1/users/oauth2/info');
  }
}

export const userService = new UserService();