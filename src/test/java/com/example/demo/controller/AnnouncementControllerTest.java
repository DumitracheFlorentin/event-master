package com.example.demo.controller;

import com.example.demo.dto.AnnouncementDTO;
import com.example.demo.entity.Announcement;
import com.example.demo.service.AnnouncementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AnnouncementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnnouncementService announcementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AnnouncementController(announcementService)).build();
    }

    @Test
    public void testCreateAnnouncement() throws Exception {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Anunț important");
        announcementDTO.setContent("Acesta este un anunț important pentru toți utilizatorii.");

        when(announcementService.createAnnouncement(any(AnnouncementDTO.class))).thenReturn(new Announcement());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Anunț important\", \"content\": \"Acesta este un anunț important pentru toți utilizatorii.\"}"))
                .andExpect(status().isCreated()) // Expect HTTP status 201 Created
                .andReturn();

        verify(announcementService, times(1)).createAnnouncement(any(AnnouncementDTO.class));
    }

    @Test
    public void testGetAnnouncementById() throws Exception {
        Announcement announcement = new Announcement();
        announcement.setId(1L);

        when(announcementService.getAnnouncementById(eq(1L))).thenReturn(announcement);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/announcements/1"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L)) // Verify the announcement's ID in the response
                .andReturn();

        verify(announcementService, times(1)).getAnnouncementById(eq(1L));
    }

    @Test
    public void testGetAllAnnouncements() throws Exception {
        Announcement announcement1 = new Announcement();
        announcement1.setId(1L);
        Announcement announcement2 = new Announcement();
        announcement2.setId(2L);
        List<Announcement> announcements = Arrays.asList(announcement1, announcement2);

        when(announcementService.getAllAnnouncements()).thenReturn(announcements);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/announcements"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L)) // Verify the first announcement's ID in the response
                .andExpect(jsonPath("$[1].id").value(2L)) // Verify the second announcement's ID in the response
                .andReturn();

        verify(announcementService, times(1)).getAllAnnouncements();
    }

    @Test
    public void testUpdateAnnouncement() throws Exception {
        AnnouncementDTO updatedAnnouncementDTO = new AnnouncementDTO();
        updatedAnnouncementDTO.setContent("Acesta este un anunț actualizat.");

        Announcement updatedAnnouncement = new Announcement();
        updatedAnnouncement.setContent("Acesta este un anunț actualizat.");

        when(announcementService.updateAnnouncement(eq(1L), any(AnnouncementDTO.class))).thenReturn(updatedAnnouncement);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/announcements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Acesta este un anunț actualizat.\"}"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Acesta este un anunț actualizat.")) // Verify the updated content in the response
                .andReturn();

        verify(announcementService, times(1)).updateAnnouncement(eq(1L), any(AnnouncementDTO.class));
    }

    @Test
    public void testDeleteAnnouncement() throws Exception {
        when(announcementService.deleteAnnouncement(eq(1L))).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/announcements/1"))
                .andExpect(status().isNoContent()) // Expect HTTP status 204 No Content
                .andReturn();

        verify(announcementService, times(1)).deleteAnnouncement(eq(1L));
    }
}

