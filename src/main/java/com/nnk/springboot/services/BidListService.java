package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {

    void createBidList();
    void updateBidList(Integer id);
    List<BidList> getAllBidList();
    BidList getBidListById(Integer id);
    void deleteBidList();
}
