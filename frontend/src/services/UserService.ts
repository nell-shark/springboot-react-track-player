import { UserInfo } from '@interfaces/user-Info';

import { axiosInstance } from '@services/axios-instance';

class UserService {
  public getUserInfo() {
    return axiosInstance.get<UserInfo>('/api/v1/users/oauth2/info');
  }
}

export const userService = new UserService();
