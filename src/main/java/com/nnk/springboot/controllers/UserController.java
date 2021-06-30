package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.configuration.exception.UserAlreadyExistException;
import com.nnk.springboot.services.UserService;
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
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User initUserAttribute() {
        return new User();
    }
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.getAllUser());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@ModelAttribute @Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            LOGGER.debug("User validate form Ok");
            try {
                userService.createUser(user);
                model.addAttribute("message", "Add successful");
                model.addAttribute("user", new User());
                LOGGER.info("User added");
            } catch (UserAlreadyExistException e) {
                LOGGER.error("Error during adding User " + e.getMessage());
                model.addAttribute("message", e.getMessage());
            } catch (Exception e) {
                LOGGER.error("Error during adding User " + e.toString());
                model.addAttribute("message", "Issue during creating user, please retry later");
            }
        }
        LOGGER.debug("Error in user validate form");
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("user", userService.getUserById(id));
            return "user/update";
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting User " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/user/list";
        }
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute("user") @Valid User user,
                             BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            LOGGER.debug("Error in user update form");
            user.setId(id);
            return "user/update";
        }

        try {
            userService.updateUser(user, id);
            attributes.addFlashAttribute("message", "Update successful");
            LOGGER.info("User id " + id + " updated");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            LOGGER.error("Error during updating user id " + id + " " + e.toString());
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "Issue during updating, please retry later");
            LOGGER.error("Error during updating user id " + id + " " + e.toString());
        }
        //model.addAttribute("users", userService.getAllUser());
        LOGGER.debug("user update form Ok");
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        try {
            userService.deleteUser(id);
            attributes.addFlashAttribute("message", "Delete successful");
            LOGGER.info("User id "+ id + " deleted");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            LOGGER.error("Error during deleting User id " + id + " " + e.toString());
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "Issue during deleting, please retry later");
            LOGGER.error("Error during deleting User id " + id + " " + e.toString());
        }
        //model.addAttribute("users", userService.getAllUser());
        return "redirect:/user/list";
    }
}
