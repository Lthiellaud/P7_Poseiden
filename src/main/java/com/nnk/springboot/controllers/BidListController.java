package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                model.addAttribute("bidList", new BidList());
                model.addAttribute("message", "Add successful");
            } catch (Exception e) {
                model.addAttribute("message", "Issue during creating, please retry later");
            }
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {

        try {
            BidList bidList = bidListService.getBidListById(id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/bidList/list";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @ModelAttribute("bidList") @Valid BidList bidList,
                             BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return showUpdateForm(id, model, attributes);
        }
        try {
            bidListService.updateBidList(bidList, id);
            attributes.addFlashAttribute("message", "Update successful");
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "Issue during updating, please retry later");
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        try {
            bidListService.deleteBidList(id);
            attributes.addFlashAttribute("message", "Delete successful");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "Issue during deleting, please retry later");
        }
        return "redirect:/bidList/list";
    }
}
