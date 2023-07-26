package com.nellshark.trackplayer.controllers;

import com.nellshark.trackplayer.dto.TrackDTO;
import com.nellshark.trackplayer.models.Track;
import com.nellshark.trackplayer.models.TrackListPage;
import com.nellshark.trackplayer.services.TrackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackController.class)
class TrackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackService trackService;

    @Test
    @WithMockUser
    void getTrackListPage_ShouldReturnTrackListPage() throws Exception {
        int page = 1;
        String filter = "example";
        TrackDTO track1 = new TrackDTO(UUID.randomUUID(), "1");
        TrackDTO track2 = new TrackDTO(UUID.randomUUID(), "2");
        List<TrackDTO> tracks = List.of(track1, track2);
        TrackListPage trackListPage = new TrackListPage(page, true, tracks);

        when(trackService.getTrackListPage(eq(page), eq(filter))).thenReturn(trackListPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tracks")
                        .param("page", String.valueOf(page))
                        .param("filter", filter)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tracks").exists())
                .andExpect(jsonPath("$.tracks").isArray());

        verify(trackService).getTrackListPage(eq(page), eq(filter));
    }

    @Test
    @WithMockUser
    void getTrackById_ShouldReturnTrack() throws Exception {
        UUID trackId = UUID.randomUUID();
        Track track = Track.builder().name("1").seconds(1).build();

        when(trackService.getTrackById(eq(trackId))).thenReturn(track);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tracks/{id}", trackId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(track.getName())))
                .andExpect(jsonPath("$.seconds", is(track.getSeconds())));

        verify(trackService).getTrackById(eq(trackId));
    }

    @Test
    @WithMockUser
    void uploadTrack_ShouldReturnCreated() throws Exception {
        String name = "Sample Track";
        MockMultipartFile file = new MockMultipartFile("file", new byte[255]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/tracks")
                        .file(file)
                        .param("name", name)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(trackService).uploadTrack(eq(name), eq(file));
    }
}
