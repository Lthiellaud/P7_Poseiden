package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/sql/data.sql")
public class TradeServiceIT {

    @Autowired
    private TradeService tradeService;

    @Test
    public void createTradeTest() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("New account");
        trade.setType("Type new account");
        trade.setBuyQuantity(1.5);

        tradeService.createTrade(trade);

        assertThat(tradeService.getTradeById(3).getAccount()).isEqualTo("New account");
    }

    @Test
    public void updateTradeTest() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("Account updated");
        trade.setType("Type updated");
        trade.setBuyQuantity(1.5);

        tradeService.updateTrade(trade, 1);

        assertThat(tradeService.getTradeById(1).getAccount()).isEqualTo("Account updated");
        assertThat(tradeService.getTradeById(1).getType()).isEqualTo("Type updated");
        assertThat(tradeService.getTradeById(1).getBuyQuantity()).isEqualTo(1.5);

    }

    @Test
    public void deleteTradeTest() throws Exception {
        tradeService.deleteTrade(2);

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> tradeService.getTradeById(2));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid trade Id: 2");

    }
}
