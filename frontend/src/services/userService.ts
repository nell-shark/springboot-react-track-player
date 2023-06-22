import { axiosInstance } from '@services/axiosInstance';

import { UserInfo } from '../typings/userInfo';

class UserService {
  public getUserInfo() {
    return axiosInstance.get<UserInfo>('/api/v1/users/oauth2/info');
  }
}

export const userService = new UserService();