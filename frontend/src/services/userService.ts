import { UserState } from '@typings/userState';

import { axiosInstance } from '@services/axiosInstance';

class UserService {
  public getUserInfo() {
    return axiosInstance.get<UserState>('/api/v1/users/oauth2/info');
  }
}

export const userService = new UserService();