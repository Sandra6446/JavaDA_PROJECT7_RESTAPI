package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.domain.entity.CurvePoint;
import com.nnk.springboot.service.BidListService;
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

    private static final Logger logger = LogManager.getLogger(BidListService.class);

    @Autowired
    CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePointDto", curvePointService.getAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePointDto curvePointDto) {
        return "curvePoint/add";
    }

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

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePointDto", curvePointService.getById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDto curvePointDto,
                             BindingResult result, Model model) {
        curvePointDto.setCurveId(id);
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "curvePoint/update";
        }
        curvePointService.update(curvePointDto);
        model.addAttribute("curvePointDto", curvePointService.getAll());
        logger.info(String.format("Curve %s updated", id));
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.delete(id);
        model.addAttribute("curvePointDto", curvePointService.getAll());
        logger.info(String.format("Curve %s deleted", id));
        return "redirect:/curvePoint/list";
    }
}
