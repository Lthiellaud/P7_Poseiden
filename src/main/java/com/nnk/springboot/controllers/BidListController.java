package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.getAllBidList());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@ModelAttribute("bidList") @Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                bidListService.createBidList(bid);
                model.addAttribute("message", "Add operation successful");
            } catch (Exception e) {
                model.addAttribute("message", "The add operation had an issue, please retry later");
            }
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        try {
            BidList bidList = bidListService.getBidListById(id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            return home(model);
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @ModelAttribute("bidList") @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return showUpdateForm(id, model);
        }
        try {
            bidListService.updateBidList(bidList);
            model.addAttribute("message", "Update operation successful");
        } catch (Exception e) {
            model.addAttribute("message", "The update operation had an issue, please retry later");
        }
        return home(model);
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            bidListService.deleteBidList(id);
            model.addAttribute("message", "Delete operation successful");
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("message", "The delete operation had an issue, please retry later");
        }
        return home(model);
    }
}
