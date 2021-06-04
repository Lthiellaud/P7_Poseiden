package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BidListController.class)
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @Test
    public void getBidListListWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getBidListList() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("test");
        bidList.setType("Type1");
        bidList.setBidQuantity(10.0);
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(bidList);
        when(bidListService.getAllBidList()).thenReturn(bidLists);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("bidLists", bidLists));
    }

    @Test
    public void getBidListAddWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getBidListAdd() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void getBidListUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/bidList/update/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getBidListUpdateWithException() throws Exception {
        when(bidListService.getBidListById(0)).thenThrow(new IllegalArgumentException("Invalid bid list Id:0"));
        mockMvc.perform(get("/bidList/update/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Invalid bid list Id:0"));
    }

    @WithMockUser
    @Test
    public void getBidListUpdate() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("test");
        bidList.setType("Type1");
        bidList.setBidQuantity(10.0);
        when(bidListService.getBidListById(1)).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("bidList", bidList));
    }

    @Test
    public void getBidListDeleteWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/bidList/delete/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getBidListDelete() throws Exception {

        mockMvc.perform(get("/bidList/delete/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Delete successful"));
    }

    @Test
    @Disabled
    public void postBidListValidateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/bidList/validate"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    @Disabled
    public void postBidListValidate() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                    .param("account", "test")
                    .param("type", "type")
                    .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("/bidList/validate"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("message", "Add successful"));
    }

    @WithMockUser
    @Test
    @Disabled
    public void postBidListValidateAccountEmpty() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .param("account", " ")
                .param("type", "type")
                .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("/bidList/validate"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotBlank"));
    }

    @WithMockUser
    @Test
    @Disabled
    public void postBidListValidateTypeEmpty() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .param("account", "account")
                .param("type", "")
                .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("/bidList/validate"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "type", "NotBlank"));
    }

    @Test
    @Disabled
    public void postBidListUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/bidList/update/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    @Disabled
    public void postBidListUpdate() throws Exception {
        mockMvc.perform(post("/bidList/update/0")
                .param("account", "test")
                .param("type", "type")
                .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("/bidList/update/0"))
                .andExpect(model().hasNoErrors());
    }

    @WithMockUser
    @Test
    @Disabled
    public void postBidListUpdateAccountEmpty() throws Exception {
        mockMvc.perform(post("/bidList/update/0")
                .param("account", " ")
                .param("type", "type")
                .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("/bidList/update/0"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotBlank"));
    }

    @WithMockUser
    @Test
    @Disabled
    public void postBidListUpdateTypeEmpty() throws Exception {
        mockMvc.perform(post("/bidList/update/0")
                .param("account", "account")
                .param("type", "")
                .param("bidQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("/bidList/update/0"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "type", "NotBlank"));
    }


}