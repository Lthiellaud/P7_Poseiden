package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeService.getAllTrade());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@ModelAttribute @Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        try {
            tradeService.createTrade(trade);
            model.addAttribute("message", "Add successful");
            model.addAttribute("trade", new Trade());
            LOGGER.info("Trade added");
        } catch (Exception e) {
            LOGGER.error("Error during adding Trade " + e.toString());
            model.addAttribute("message", "Issue during creating trade, please retry later");
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("trade", tradeService.getTradeById(id));
            return "trade/update";
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting Trade " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/trade/list";
        }

    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @ModelAttribute @Valid Trade trade,
                             BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        try {
            tradeService.updateTrade(trade, id);
            attributes.addFlashAttribute("message", "Update successful");
            LOGGER.info("Trade id " + id + " updated");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            LOGGER.error("Error during deleting BindList id " + id + " " + e.toString());
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "Issue during updating, please retry later");
            LOGGER.error("Error during deleting BindList id " + id + " " + e.toString());
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        try {
            tradeService.deleteTrade(id);
            attributes.addFlashAttribute("message", "Delete successful");
            LOGGER.info("Trade id "+ id + " deleted");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            LOGGER.error("Error during deleting Trade id " + id + " " + e.toString());
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "Issue during deleting, please retry later");
            LOGGER.error("Error during deleting Trade id " + id + " " + e.toString());
        }
        return "redirect:/trade/list";
    }
}
