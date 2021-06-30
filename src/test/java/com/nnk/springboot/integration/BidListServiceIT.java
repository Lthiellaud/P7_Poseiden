package com.nnk.springboot.integration;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Disabled;
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
public class BidListServiceIT {

    @Autowired
    private BidListService bidListService;

    @Test
    public void createBidListTest () throws Exception {
        BidList bidList = new BidList();
        bidList.setAccount("New account");
        bidList.setType("Type new account");
        bidList.setBidQuantity(1.5);

        bidListService.createBidList(bidList);

        assertThat(bidListService.getBidListById(3).getAccount()).isEqualTo("New account");
    }

    @Test
    public void updateBidListTest() throws Exception {
        BidList bidList = new BidList();
        bidList.setAccount("Account updated");
        bidList.setType("Type updated");
        bidList.setBidQuantity(1.5);

        bidListService.updateBidList(bidList, 1);

        assertThat(bidListService.getBidListById(1).getAccount()).isEqualTo("Account updated");
        assertThat(bidListService.getBidListById(1).getType()).isEqualTo("Type updated");
        assertThat(bidListService.getBidListById(1).getBidQuantity()).isEqualTo(1.5);
        assertThat(bidListService.getBidListById(1).getBenchmark()).isEqualTo("benchmark");

    }

    @Test
    public void deleteBidListTest() throws Exception {
        bidListService.deleteBidList(2);

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> bidListService.getBidListById(2));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid bid list Id: 2");

    }
}
