package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BidListServiceTest {

    @MockBean
    private BidListRepository bidListRepository;

    @Autowired
    private BidListService bidListService;

    private static BidList bidList;

    @BeforeEach
    public void initTest() {
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("");
        bidList.setType("");
        bidList.setBidQuantity(10.0);
    }

    @Test
    void createBidListTest() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);
        bidListService.createBidList(bidList);

        verify(bidListRepository, times(1)).save(any(BidList.class));
    }

    @Test
    void updateBidListTest() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);
        bidListService.updateBidList(bidList, 1);

        verify(bidListRepository, times(1)).save(any(BidList.class));
    }

    @Test
    void getBidListByIdTest() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        BidList bidList1 = bidListService.getBidListById(1);

        verify(bidListRepository, times(1)).findById(1);
        assertThat(bidList1).isEqualTo(bidList);
    }

    @Test
    void getBidListByIdNotFoundedTest() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> bidListService.getBidListById(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid bid list Id: 1");
        verify(bidListRepository, times(1)).findById(1);

    }

    @Test
    void deleteBidListNotFoundedTest() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> bidListService.deleteBidList(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid bid list Id: 1");
        verify(bidListRepository, times(1)).findById(1);

    }
}