package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUserListWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getUserList() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.setFullname("User Test");
        user.setRole("USER");
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.getAllUser()).thenReturn(users);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("users", users));
    }

    @Test
    public void getUserAddWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getUserAdd() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void getUserUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/user/update/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getUserUpdateWithException() throws Exception {
        when(userService.getUserById(0)).thenThrow(new IllegalArgumentException("Invalid user Id:0"));
        mockMvc.perform(get("/user/update/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Invalid user Id:0"));
    }

    @WithMockUser
    @Test
    public void getUserUpdate() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.setFullname("User Test");
        user.setRole("USER");
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void getUserDeleteWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/user/delete/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getUserDelete() throws Exception {

        mockMvc.perform(get("/user/delete/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Delete successful"));
    }

    @Test
    public void postUserValidateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/user/validate")
                .with(csrf()))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void postUserValidate() throws Exception {
        mockMvc.perform(post("/user/validate")
                    .param("account", "test")
                    .param("type", "type")
                    .param("buyQuantity", "10")
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("message", "Add successful"));
    }

    @WithMockUser
    @Test
    public void postUserValidateAccountEmpty() throws Exception {
        mockMvc.perform(post("/user/validate")
                .param("account", " ")
                .param("type", "type")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("user", "account", "NotBlank"));
    }

    @WithMockUser
    @Test
    public void postUserValidateTypeEmpty() throws Exception {
        mockMvc.perform(post("/user/validate")
                .param("account", "account")
                .param("type", "")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("user", "type", "NotBlank"));
    }

    @Test
    public void postUserUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/user/update/0")
                .with(csrf()))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void postUserUpdate() throws Exception {

        mockMvc.perform(post("/user/update/0")
                .param("account", "test")
                .param("type", "type")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().hasNoErrors());
    }

    @WithMockUser
    @Test
    public void postUserUpdateAccountEmpty() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.setFullname("User Test");
        user.setRole("USER");
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(post("/user/update/1")
                .param("account", "")
                .param("type", "type")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("user", "account", "NotBlank"));
    }

    @WithMockUser
    @Test
    public void postUserUpdateTypeEmpty() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.setFullname("User Test");
        user.setRole("USER");
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(post("/user/update/1")
                .param("account", "account")
                .param("type", "")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("user", "type", "NotBlank"));
    }


}