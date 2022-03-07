package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.service.CurvePointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class CurveController {

    private static final Logger logger = LogManager.getLogger(CurveController.class);

    @Autowired
    CurvePointService curvePointService;

    /**
     * Renders the view "curvePoint/list" with the list of all curvePoint
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "curvePoint/list" to render the associated view
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePointDto", curvePointService.getAll());
        return "curvePoint/list";
    }

    /**
     * Renders the view "curvePoint/add" with a form to add a curvePoint
     *
     * @return The string "curvePoint/add" to render the associated view
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        model.addAttribute(new CurvePointDto());
        return "curvePoint/add";
    }

    /**
     * Redirects to the view "curvePoint/list" with the list of all curvePoint if the curvePoint saving succeed
     *
     * @param curvePointDto : The curvePointDto to create
     * @param result : The binding results
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/curvePointDto/list" to redirect to the associated view if the operation succeeds, otherwise the string "curvePointDto/add" to display the reason of the failure on the associated view
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDto curvePointDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "curvePoint/add";
        }
        curvePointService.save(curvePointDto);
        model.addAttribute("curvePointDto", curvePointService.getAll());
        logger.info(String.format("Curve %s saved in database", curvePointDto.getId()));
        return "redirect:/curvePoint/list";
    }

    /**
     * Renders the view "curvePoint/update" with a prefilled form to update a curvePoint
     *
     * @param id : The id of the curvePoint to be updated
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "curvePoint/update" to render the associated view
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePointDto", curvePointService.getById(id));
        return "curvePoint/update";
    }

    /**
     * Redirects to the view "curvePoint/list" with the list of all curvePoint if the curvePoint updating succeed
     *
     * @param id : The id of the curvePoint to be updated
     * @param curvePointDto : The curvePoint with new values
     * @param result : The binding results
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/curvePoint/list" to redirect to the associated view if the operation succeeds, otherwise the string "curvePoint/update" to display the reason of the failure on the associated view
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDto curvePointDto,
                            BindingResult result, Model model) {
        curvePointDto.setId(id);
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "curvePoint/update";
        }
        curvePointService.update(curvePointDto);
        model.addAttribute("curvePointDto", curvePointService.getAll());
        logger.info(String.format("Curve %s updated", id));
        return "redirect:/curvePoint/list";
    }

    /**
     * Redirects to the view "curvePoint/list" with the list of all curvePoint if the curvePoint updating succeed
     *
     * @param id : The id of the curvePoint to be deleted
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/curvePoint/list" to redirect to the associated view if the operation succeeds, otherwise the reason of the failure
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.delete(id);
        model.addAttribute("curvePointDto", curvePointService.getAll());
        logger.info(String.format("Curve %s deleted", id));
        return "redirect:/curvePoint/list";
    }
}
