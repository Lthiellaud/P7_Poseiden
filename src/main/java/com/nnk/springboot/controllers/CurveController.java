package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CurveController.class);

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoint());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@ModelAttribute @Valid CurvePoint curvePoint, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        try {
            curvePointService.createCurvePoint(curvePoint);
            LOGGER.info("Curve point added");
            model.addAttribute("curvePoint", new CurvePoint());
            model.addAttribute("message", "Add successful");
        } catch (Exception e) {
            LOGGER.error("Error during adding curve point " + e.toString());
            model.addAttribute("message", "Issue during creating curve point, please retry later");
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("curvePoint", curvePointService.getCurvePointById(id));
            return "curvePoint/update";
        } catch (IllegalArgumentException e){
            LOGGER.error("Error during getting curve point " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/curvePoint/list";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @ModelAttribute @Valid CurvePoint curvePoint,
                              BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        try {
            curvePointService.updateCurvePoint(curvePoint, id);
            LOGGER.info("Curve point id " + id + "added");
            attributes.addFlashAttribute("message", "Update successful");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting curve point " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during getting curve point " + e.toString());
            attributes.addFlashAttribute("message", "Issue during updating, please retry later");
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        try {
            curvePointService.deleteCurvePoint(id);
            LOGGER.info("Curve point id " + id + " deleted");
            attributes.addFlashAttribute("message", "Delete successful");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during getting curve point id " + id + " " + e.toString());
            attributes.addFlashAttribute("message", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during deleting curve point " + e.toString());
            attributes.addFlashAttribute("message", "Issue during deleting, please retry later");
        }
        return "redirect:/curvePoint/list";
    }
}
