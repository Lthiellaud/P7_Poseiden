package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {
    void createTrade(Trade trade) throws Exception;
    void updateTrade(Trade trade, Integer id) throws Exception;
    List<Trade> getAllTrade();
    Trade getTradeById(Integer id);
    void deleteTrade(Integer id) throws Exception;
}
