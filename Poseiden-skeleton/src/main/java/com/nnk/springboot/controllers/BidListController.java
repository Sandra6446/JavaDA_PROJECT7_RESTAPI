package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.service.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BidListController {

    private static final Logger logger = LogManager.getLogger(BidListService.class);

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidListDto", bidListService.getAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidListDto bidListDto) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListDto bidListDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "bidList/add";
        }
        bidListService.save(bidListDto);
        model.addAttribute("bidListDto", bidListService.getAll());
        logger.info(String.format("Bid %s saved in database", bidListDto.getBidListId()));
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidListDto", bidListService.getById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDto bidListDto,
                            BindingResult result, Model model) {
        bidListDto.setBidListId(id);
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "bidList/update";
        }
        bidListService.update(bidListDto);
        model.addAttribute("bidListDto", bidListService.getAll());
        logger.info(String.format("Bid %s updated", id));
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.delete(id);
        model.addAttribute("bidListDto", bidListService.getAll());
        logger.info(String.format("Bid %s deleted", id));
        return "redirect:/bidList/list";
    }
}
