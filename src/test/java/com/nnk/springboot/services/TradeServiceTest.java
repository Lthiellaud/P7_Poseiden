package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TradeServiceTest {

    @MockBean
    private TradeRepository tradeRepository;

    @Autowired
    private TradeService tradeService;

    private static Trade trade;

    @BeforeEach
    public void initTest() {
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("");
        trade.setType("");
        trade.setBuyQuantity(10.0);
    }

    @Test
    void createTradeTest() throws Exception {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
        tradeService.createTrade(trade);

        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    void updateTradeTest() throws Exception {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
        tradeService.updateTrade(trade, 1);

        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    void getTradeByIdTest() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        Trade trade1 = tradeService.getTradeById(1);

        verify(tradeRepository, times(1)).findById(1);
        assertThat(trade1).isEqualTo(trade);
    }

    @Test
    void getTradeByIdNotFoundedTest() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> tradeService.getTradeById(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid trade Id: 1");
        verify(tradeRepository, times(1)).findById(1);

    }

    @Test
    void deleteTradeNotFoundedTest() throws Exception {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> tradeService.deleteTrade(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid trade Id: 1");
        verify(tradeRepository, times(1)).findById(1);

    }
}