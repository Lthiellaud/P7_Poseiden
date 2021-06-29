package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.implementation.UserDetailsServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void getRatingListWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().is(302));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void getRatingList() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("");
        rating.setSandPRating("");
        rating.setFitchRating("");
        rating.setOrderNumber(0);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);
        when(ratingService.getAllRating()).thenReturn(ratings);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("ratings", ratings))
                .andExpect(content().string(containsString("&nbsp;|&nbsp;<a href=\"/user/list\">User</a>")));
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    public void getRatingListAsUSER() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("");
        rating.setSandPRating("");
        rating.setFitchRating("");
        rating.setOrderNumber(0);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);
        when(ratingService.getAllRating()).thenReturn(ratings);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("ratings", ratings))
                .andExpect(content().string(not(containsString("&nbsp;|&nbsp;<a href=\"/user/list\">User</a>"))));
    }

    @Test
    public void getRatingAddWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().is(302));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void getRatingAdd() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void getRatingUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/rating/update/0"))
                .andExpect(status().is(302));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void getRatingUpdateWithException() throws Exception {
        when(ratingService.getRatingById(0)).thenThrow(new IllegalArgumentException("Invalid bid list Id:0"));
        mockMvc.perform(get("/rating/update/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Invalid bid list Id:0"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void getRatingUpdate() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("");
        rating.setSandPRating("");
        rating.setFitchRating("");
        rating.setOrderNumber(0);
        when(ratingService.getRatingById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("rating", rating));
    }

    @Test
    public void getRatingDeleteWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/rating/delete/0"))
                .andExpect(status().is(302));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void getRatingDelete() throws Exception {

        mockMvc.perform(get("/rating/delete/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Delete successful"));
    }

    @Test
    public void postRatingValidateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .with(csrf()))
                .andExpect(status().is(302));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void postRatingValidate() throws Exception {
        mockMvc.perform(post("/rating/validate")
                    .param("moodysRating", "moody")
                    .param("sandPRating", "sand")
                    .param("fitchRating", "fitch")
                    .param("orderNumber", "10")
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("message", "Add successful"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void postRatingValidateOrderNull() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
                .param("fitchRating", "fitch")
                .param("orderNumber", "")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "NotNull"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void postRatingValidateMoodysEmpty() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "")
                .param("sandPRating", "sand")
                .param("fitchRating", "fitch")
                .param("orderNumber", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("rating", "moodysRating", "NotBlank"));
    }

    @Test
    public void postRatingUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/rating/update/0")
                .with(csrf()))
                .andExpect(status().is(302));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void postRatingUpdate() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
                .param("fitchRating", "fitch")
                .param("orderNumber", "10")
                .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Update successful"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void postRatingUpdateFitchRatingEmpty() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("");
        rating.setSandPRating("");
        rating.setFitchRating("");
        rating.setOrderNumber(0);
        when(ratingService.getRatingById(1)).thenReturn(rating);

        mockMvc.perform(post("/rating/update/0")
                .param("moodysRating", "moody")
                .param("sandPRating", "sand")
                .param("fitchRating", "")
                .param("orderNumber", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("rating", "fitchRating", "NotBlank"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    public void postRatingUpdateSandPRatingEmpty() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("");
        rating.setSandPRating("");
        rating.setFitchRating("");
        rating.setOrderNumber(0);
        when(ratingService.getRatingById(1)).thenReturn(rating);

        mockMvc.perform(post("/rating/update/0")
                .param("moodysRating", "moody")
                .param("sandPRating", "")
                .param("fitchRating", "fitch")
                .param("orderNumber", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("rating", "sandPRating", "NotBlank"));
    }


}