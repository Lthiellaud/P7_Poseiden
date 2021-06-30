package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.sql.SQLException;
import java.util.List;

public interface BidListService {

    void createBidList(BidList bid) throws Exception;
    void updateBidList(BidList bid, Integer id) throws Exception;
    List<BidList> getAllBidList();
    BidList getBidListById(Integer id);
    void deleteBidList(Integer id) throws Exception;
}
