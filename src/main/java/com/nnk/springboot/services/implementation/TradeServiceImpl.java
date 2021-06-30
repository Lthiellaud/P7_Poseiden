package com.nnk.springboot.services.implementation;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Creates a new Trade.
     * @param trade the Trade to be created
     */
    @Override
    public void createTrade(Trade trade) throws Exception {
        trade.setCreationDate(new Timestamp(System.currentTimeMillis()));
        tradeRepository.save(trade);
    }

    /**
     * Updates a trade.
     * @param trade the trade to be updated
     * @param id id of the trade to be updated
     */
    @Override
    public void updateTrade(Trade trade, Integer id) throws Exception {
        Trade updatedTrade = getTradeById(id);
        updatedTrade.setAccount(trade.getAccount());
        updatedTrade.setBuyQuantity(trade.getBuyQuantity());
        updatedTrade.setType(trade.getType());
        updatedTrade.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        tradeRepository.save(updatedTrade);
    }

    /**
     * Gives all the trades
     * @return all the trades
     */
    @Override
    public List<Trade> getAllTrade() {
        return tradeRepository.findAll();
    }

    /**
     * returns a trade from an id
     * @param id the id
     * @return the trade
     */
    @Override
    public Trade getTradeById(Integer id) throws IllegalArgumentException {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id: " + id));
    }

    /**
     * delete a trade from an id
     * @param id the id
     */
    @Override
    public void deleteTrade(Integer id) throws Exception {
        tradeRepository.delete(getTradeById(id));
    }
}
