package com.nellshark.trackplayer.controllers;

import com.nellshark.trackplayer.dto.AppOAuth2UserDTO;
import com.nellshark.trackplayer.services.AppOAuth2UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppOAuth2UserController.class)
@AutoConfigureMockMvc
class AppOAuth2UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppOAuth2UserService appOAuth2UserService;

    @Test
    void getAppOAuth2User_ShouldReturnUserDTO() throws Exception {
        OAuth2User oauth2User = new DefaultOAuth2User(List.of(), Map.of("sub", "1234567890"), "sub");
        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(oauth2User, List.of(), "github");
        AppOAuth2UserDTO userDTO = new AppOAuth2UserDTO("John", "url", List.of());

        when(appOAuth2UserService.getAppOAuth2UserDTO(oauth2User)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/oauth2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(authentication)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(userDTO.login())))
                .andExpect(jsonPath("$.avatarUrl", is(userDTO.avatarUrl())));
    }

    @Test
    void addFavoriteTrackToUser_ShouldReturnOk() throws Exception {
        OAuth2User oauth2User = new DefaultOAuth2User(List.of(), Map.of("sub", "1234567890"), "sub");
        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(oauth2User, List.of(), "github");
        UUID trackId = UUID.randomUUID();

        doNothing().when(appOAuth2UserService).addFavoriteTrackToUser(trackId, oauth2User);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/oauth2/favorite/track/" + trackId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(authentication))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void removeFavoriteTrackToUser_ShouldReturnOk() throws Exception {
        OAuth2User oauth2User = new DefaultOAuth2User(List.of(), Map.of("sub", "1234567890"), "sub");
        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(oauth2User, List.of(), "github");
        UUID trackId = UUID.randomUUID();

        doNothing().when(appOAuth2UserService).removeUserFavoriteTrack(trackId, oauth2User);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/oauth2/favorite/track/" + trackId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(authentication))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
