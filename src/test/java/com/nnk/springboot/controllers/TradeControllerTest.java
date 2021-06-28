package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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

@WebMvcTest(controllers = TradeController.class)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    public void getTradeListWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getTradeList() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("test");
        trade.setType("Type1");
        trade.setBuyQuantity(10.0);
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        when(tradeService.getAllTrade()).thenReturn(trades);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("trades", trades));
    }

    @Test
    public void getTradeAddWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getTradeAdd() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void getTradeUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/trade/update/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getTradeUpdateWithException() throws Exception {
        when(tradeService.getTradeById(0)).thenThrow(new IllegalArgumentException("Invalid trade Id:0"));
        mockMvc.perform(get("/trade/update/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Invalid trade Id:0"));
    }

    @WithMockUser
    @Test
    public void getTradeUpdate() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("test");
        trade.setType("Type1");
        trade.setBuyQuantity(10.0);
        when(tradeService.getTradeById(1)).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("trade", trade));
    }

    @Test
    public void getTradeDeleteWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/trade/delete/0"))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void getTradeDelete() throws Exception {

        mockMvc.perform(get("/trade/delete/0"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("message", "Delete successful"));
    }

    @Test
    public void postTradeValidateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .with(csrf()))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void postTradeValidate() throws Exception {
        mockMvc.perform(post("/trade/validate")
                    .param("account", "test")
                    .param("type", "type")
                    .param("buyQuantity", "10")
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("message", "Add successful"));
    }

    @WithMockUser
    @Test
    public void postTradeValidateAccountEmpty() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .param("account", " ")
                .param("type", "type")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("trade", "account", "NotBlank"));
    }

    @WithMockUser
    @Test
    public void postTradeValidateTypeEmpty() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .param("account", "account")
                .param("type", "")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("trade", "type", "NotBlank"));
    }

    @Test
    public void postTradeUpdateWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/trade/update/0")
                .with(csrf()))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void postTradeUpdate() throws Exception {

        mockMvc.perform(post("/trade/update/0")
                .param("account", "test")
                .param("type", "type")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().hasNoErrors());
    }

    @WithMockUser
    @Test
    public void postTradeUpdateAccountEmpty() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("test");
        trade.setType("Type1");
        trade.setBuyQuantity(10.0);
        when(tradeService.getTradeById(1)).thenReturn(trade);

        mockMvc.perform(post("/trade/update/1")
                .param("account", "")
                .param("type", "type")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("trade", "account", "NotBlank"));
    }

    @WithMockUser
    @Test
    public void postTradeUpdateTypeEmpty() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("test");
        trade.setType("Type1");
        trade.setBuyQuantity(10.0);
        when(tradeService.getTradeById(1)).thenReturn(trade);

        mockMvc.perform(post("/trade/update/1")
                .param("account", "account")
                .param("type", "")
                .param("buyQuantity", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("trade", "type", "NotBlank"));
    }


}