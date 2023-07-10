import { axiosInstance } from '@services/axiosInstance';

import { User } from '@typings/user';

class UserService {
  public getOAuth2UserInfo() {
    return axiosInstance.get<User>('/api/v1/users/oauth2/info');
  }

  public addFavoriteTrack(trackId: string) {
    return axiosInstance.post('/api/v1/users/oauth2/favorite/track', {
      trackId
    });
  }

  public logout() {
    return axiosInstance.post('/logout');
  }
}

export const userService = new UserService();
