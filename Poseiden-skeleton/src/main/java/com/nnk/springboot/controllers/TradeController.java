package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.TradeDto;
import com.nnk.springboot.service.TradeService;
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
public class TradeController {

    private static final Logger logger = LogManager.getLogger(TradeController.class);

    @Autowired
    TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(TradeDto tradeDto) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDto tradeDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "trade/add";
        }
        tradeService.save(tradeDto);
        model.addAttribute("tradeDto", tradeService.getAll());
        logger.info(String.format("Trade %s saved in database", tradeDto.getTradeId()));
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("tradeDto", tradeService.getById(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDto tradeDto,
                              BindingResult result, Model model) {
        tradeDto.setTradeId(id);
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "trade/update";
        }
        tradeService.update(tradeDto);
        model.addAttribute("trades", tradeService.getAll());
        logger.info(String.format("Trade %s updated", id));
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.delete(id);
        model.addAttribute("trades", tradeService.getAll());
        logger.info(String.format("Trade %s deleted", id));
        return "redirect:/trade/list";
    }
}
