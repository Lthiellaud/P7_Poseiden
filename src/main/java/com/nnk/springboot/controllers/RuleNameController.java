package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameService.getAllRuleName());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@ModelAttribute @Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        try {
            ruleNameService.createRuleName(ruleName);
            LOGGER.info("Rule Name added");
            model.addAttribute("ruleName", new RuleName());
            model.addAttribute("message", "Add successful");
        } catch (Exception e) {
            LOGGER.error("Error during adding rule name " + e.toString());
            model.addAttribute("message", "Issue during creating rule name, please retry later");
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("ruleName",ruleNameService.getRuleNameById(id));
            return "ruleName/update";
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting rule name " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @ModelAttribute @Valid RuleName ruleName,
                             BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            ruleName.setId(id);
            return "ruleName/update";
        }
        try {
            ruleNameService.updateRuleName(ruleName, id);
            LOGGER.info("Rule name id " + id + "updated");
            attributes.addFlashAttribute("message", "Update successful");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during updating rule name " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during updating rule name " + e.toString());
            attributes.addFlashAttribute("message", "Issue during updating, please retry later");
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        try {
            ruleNameService.deleteRuleName(id);
            LOGGER.info("Rule name id " + id + "deleted");
            attributes.addFlashAttribute("message", "Delete successful");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during deleting rule name " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during deleting rule name " + e.toString());
            attributes.addFlashAttribute("message", "Issue during deleting, please retry later");
        }
        return "redirect:/ruleName/list";
    }
}
