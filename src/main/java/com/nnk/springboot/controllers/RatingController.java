package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {

    @Autowired
    private RatingService ratingService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);
    
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.getAllRating());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@ModelAttribute @Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        try {
            ratingService.createRating(rating);
            LOGGER.info("Rating added");
            model.addAttribute("message", "Add successful");
            model.addAttribute("rating", new Rating());
        } catch (Exception e) {
            LOGGER.error("Error during adding rating " + e.toString());
            model.addAttribute("message", "Issue during creating rating, please retry later");
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("rating", ratingService.getRatingById(id));
            return "rating/update";
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting rating " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/rating/list";
        }
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @ModelAttribute @Valid Rating rating,
                             BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        try {
            ratingService.updateRating(rating, id);
            LOGGER.info("Update of rating id " + id + " successful");
            attributes.addFlashAttribute("message", "Update successful");            
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting rating " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during updating rating " + e.toString());
            attributes.addFlashAttribute("message", "Issue during updating, please retry later");
        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        try {
            ratingService.deleteRating(id);
            LOGGER.info("Delete of rating id " + id + " successful");
            attributes.addFlashAttribute("message", "Delete successful");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting rating " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during deleting rating " + e.toString());
            attributes.addFlashAttribute("message", "Issue during updating, please retry later");
        }
        return "redirect:/rating/list";
    }
}
