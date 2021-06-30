package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * To manage CRUD operations for BidList
 */
@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);

    /**
     * To return bid list page
     * @param model filled with list of all bid list
     * @return bid list page
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.getAllBidList());
        return "bidList/list";
    }

    /**
     * To display the add form
     * @param model initialised with a new bid list
     * @return the add form
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    /**
     * To create a bid list
     * @param bid the bid list entered
     * @param result the eventual errors in the form
     * @param model model of the bid list to be created, initialised with a new bid list if success
     * @return The add form, either with binding errors or with a new bid list
     */
    @PostMapping("/bidList/validate")
    public String validate(@ModelAttribute("bidList") @Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                bidListService.createBidList(bid);
                LOGGER.info("BidList added");
                model.addAttribute("bidList", new BidList());
                model.addAttribute("message", "Add successful");
            } catch (Exception e) {
                LOGGER.error("Error during adding bind list " + e.toString());
                model.addAttribute("message", "Issue during creating, please retry later");
            }
        }
        return "bidList/add";
    }

    /**
     * To display the update form initialised with the data of the bid list to be updated
     * @param id id of the bid list to be updated
     * @param model model with the bid list to be updated
     * @param attributes Message to be displayed on redirect page
     * @return update form if success, bid list list otherwise
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {

        try {
            BidList bidList = bidListService.getBidListById(id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting BindList " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/bidList/list";
        }
    }

    /**
     * To update a bid list
     * @param id id of the bid list to be updated
     * @param bidList Updated data for the bidlist
     * @param result the eventual errors in the form
     * @param attributes Message to be displayed on redirect page
     * @return bid list list if success, update form with errors otherwise
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @ModelAttribute("bidList") @Valid BidList bidList,
                             BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            bidList.setBidListId(id);
            return "bidList/update";
        }
        try {
            bidListService.updateBidList(bidList, id);
            LOGGER.info("BidList id " + id + " updated");
            attributes.addFlashAttribute("message", "Update successful");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during updating bind list " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during updating bind list " + e.toString());
            attributes.addFlashAttribute("message", "Issue during updating, please retry later");
        }
        return "redirect:/bidList/list";
    }

    /**
     * To delete a bid list
     * @param id id of the bid list to be updated
     * @param attributes Message to be displayed on redirect page
     * @return bid list list page
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        try {
            bidListService.deleteBidList(id);
            LOGGER.info("BidList id " + id + " deleted");
            attributes.addFlashAttribute("message", "Delete successful");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during deleting bind list id " + id + " " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during deleting bind list " + id + " "  + e.toString());
            attributes.addFlashAttribute("message", "Issue during deleting, please retry later");
        }
        return "redirect:/bidList/list";
    }
}
