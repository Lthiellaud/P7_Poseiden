package com.nnk.springboot.services.implementation;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Creates a new BidList.
     * @param bidList the BidList to be created
     */
    @Override
    public void createBidList(BidList bidList) {
        bidList.setBidListDate(new Timestamp(System.currentTimeMillis()));
        bidListRepository.save(bidList);
    }

    /**
     * Updates a bid list.
     * @param bidList the bid list to be updated
     * @param id id of the bidlist to be updated
     */
    @Override
    public void updateBidList(BidList bidList, Integer id) {
        BidList updatedBidList = getBidListById(id);
        updatedBidList.setAccount(bidList.getAccount());
        updatedBidList.setBidQuantity(bidList.getBidQuantity());
        updatedBidList.setType(bidList.getType());
        updatedBidList.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        bidListRepository.save(updatedBidList);
    }

    /**
     * Gives all the bid lists
     * @return all the bid lists
     */
    @Override
    public List<BidList> getAllBidList() {
        return bidListRepository.findAll();
    }

    /**
     * returns a bid list from an id
     * @param id the id
     * @return the bid list
     */
    @Override
    public BidList getBidListById(Integer id) throws IllegalArgumentException {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid list Id: " + id));
    }

    /**
     * delete a bid list from an id
     * @param id the id
     */
    @Override
    public void deleteBidList(Integer id) throws IllegalArgumentException {
        bidListRepository.delete(getBidListById(id));
    }
}
