package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

@WebMvcTest(controllers = RuleNameController.class)
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    public void getRuleNameListWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getRuleNameList() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("Rule name");
        ruleName.setDescription("Rule description");
        ruleName.setId(1);
        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(ruleName);
        when(ruleNameService.getAllRuleName()).thenReturn(ruleNames);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("ruleNames", ruleNames));
    }

    @Test
    public void getRuleNameAddWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getRuleNameAdd() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void getRuleNameUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/ruleName/update/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getRuleNameUpdateWithException() throws Exception {
        when(ruleNameService.getRuleNameById(0)).thenThrow(new IllegalArgumentException("Invalid curve point Id:0"));
        mockMvc.perform(get("/ruleName/update/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Invalid curve point Id:0"));
    }

    @WithMockUser
    @Test
    public void getRuleNameUpdate() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("Rule name");
        ruleName.setDescription("Description");
        ruleName.setId(1);
        when(ruleNameService.getRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("ruleName", ruleName));
    }

    @Test
    public void getRuleNameDeleteWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/ruleName/delete/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getRuleNameDelete() throws Exception {

        mockMvc.perform(get("/ruleName/delete/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Delete successful"));
    }

    @Test
    public void postRuleNameValidateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf()))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void postRuleNameValidate() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                    .param("name", "Rule name")
                    .param("description", "Rule description")
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("message", "Add successful"));
    }

    @WithMockUser
    @Test
    public void postRuleNameValidateNameBlank() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                    .param("name", " ")
                    .param("description", "Rule description")
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"));
    }

    @Test
    public void postRuleNameUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/ruleName/update/0")
                .with(csrf()))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void postRuleNameUpdate() throws Exception {
        mockMvc.perform(post("/ruleName/update/0")
                    .param("name", "Rule name")
                    .param("description", "Rule description")
                    .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Update successful"));
    }

    @WithMockUser
    @Test
    public void postRuleNameUpdateNameBlank() throws Exception {
        mockMvc.perform(post("/ruleName/update/0")
                    .param("name", "")
                    .param("description", "Rule description")
                    .param("value", "10")
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"));
    }

}