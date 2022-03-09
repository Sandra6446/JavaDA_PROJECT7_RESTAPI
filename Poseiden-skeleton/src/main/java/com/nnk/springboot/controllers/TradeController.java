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

    /**
     * Renders the view "trade/list" with the list of all trades
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "trade/list" to render the associated view
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }

    /**
     * Renders the view "trade/add" with a form to add a trade
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "trade/add" to render the associated view
     */
    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute(new TradeDto());
        return "trade/add";
    }

    /**
     * Redirects to the view "trade/list" with the list of all trades if the trade saving succeed
     *
     * @param tradeDto : The tradeDto to create
     * @param result   : The binding results
     * @param model    : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/trade/list" to redirect to the associated view if the operation succeeds, otherwise the string "trade/add" to display the reason of the failure on the associated view
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDto tradeDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "trade/add";
        }
        tradeService.save(tradeDto);
        model.addAttribute("tradeDto", tradeService.getAll());
        logger.info("Trade saved in database");
        return "redirect:/trade/list";
    }

    /**
     * Renders the view "trade/update" with a prefilled form to update a trade
     *
     * @param id    : The id of the trade to be updated
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "trade/update" to render the associated view
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("tradeDto", tradeService.getById(id));
        return "trade/update";
    }

    /**
     * Redirects to the view "trade/list" with the list of all trades if the trade updating succeed
     *
     * @param id       : The id of the trade to be updated
     * @param tradeDto : The tradeDto with new values
     * @param result   : The binding results
     * @param model    : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/trade/list" to redirect to the associated view if the operation succeeds, otherwise the string "trade/update" to display the reason of the failure on the associated view
     */
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

    /**
     * Redirects to the view "trade/list" with the list of all trades if the trade updating succeed
     *
     * @param id    : The id of the trade to be deleted
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/trade/list" to redirect to the associated view if the operation succeeds, otherwise the reason of the failure
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.delete(id);
        model.addAttribute("trades", tradeService.getAll());
        logger.info(String.format("Trade %s deleted", id));
        return "redirect:/trade/list";
    }
}
