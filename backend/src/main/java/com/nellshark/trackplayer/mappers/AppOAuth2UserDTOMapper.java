package com.nellshark.trackplayer.mappers;

import com.nellshark.trackplayer.dto.AppOAuth2UserDTO;
import com.nellshark.trackplayer.models.AppOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AppOAuth2UserDTOMapper {
    public AppOAuth2UserDTO toDTO(OAuth2User principal, AppOAuth2User appOAuth2User) {
        return new AppOAuth2UserDTO(
                principal.getAttribute("login"),
                principal.getAttribute("avatar_url"),
                appOAuth2User.getFavoriteTracks()
        );
    }
}
