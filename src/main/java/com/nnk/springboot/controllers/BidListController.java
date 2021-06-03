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
    // TODO: Inject Bid service

    @Autowired
    private BidListService bidListService;

    @ModelAttribute("bidList")
    public BidList getBidListObject(){
        return new BidList();
    }
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.getAllBidList());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm() {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@ModelAttribute("bidList") @Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                bidListService.createBidList();
                model.addAttribute("message", "Add operation successful");
            } catch (Exception e) {
                model.addAttribute("message", "The add operation had an issue, please retry later");
            }
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidList", bidListService.getBidListById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @ModelAttribute("bidList") @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return showUpdateForm(id, model);
        }
        try {
            bidListService.updateBidList(id);
            model.addAttribute("message", "Update operation successful");
        } catch (Exception e) {
            model.addAttribute("message", "The update operation had an issue, please retry later");
        }
        return home(model);
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            bidListService.updateBidList(id);
            model.addAttribute("message", "Delete operation successful");
        } catch (Exception e) {
            model.addAttribute("message", "The delete operation had an issue, please retry later");
        }
        return home(model);
    }
}
