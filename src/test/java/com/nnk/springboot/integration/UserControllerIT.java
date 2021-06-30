package com.nnk.springboot.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/sql/data.sql")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @WithUserDetails("admin")
    @Test
    public void postUserValidateWithExistingUser() throws Exception {

        mockMvc.perform(post("/user/validate")
                .param("username", "user")
                .param("fullname", "User Test")
                .param("role", "USER")
                .param("password", "User@Test5")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("message", "This username already exists, please, choose an other one"));
    }
}
