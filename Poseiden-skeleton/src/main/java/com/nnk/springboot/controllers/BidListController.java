package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {

    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @Autowired
    private BidListService bidListService;

    /**
     * Renders the view "bidList/list" with the list of all bidList
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "bidlist/list" to render the associated view
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidListDto", bidListService.getAll());
        return "bidList/list";
    }

    /**
     * Renders the view "bidList/add" with a form to add a bidList
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "bidList/add" to render the associated view
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute(new BidListDto());
        return "bidList/add";
    }

    /**
     * Redirects to the view "bidList/list" with the list of all bidList if the bidList saving succeed
     *
     * @param bidListDto : The bidListDto to create
     * @param result     : The binding results
     * @param model      : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/bidList/list" to redirect to the associated view if the operation succeeds, otherwise the string "bidList/add" to display the reason of the failure on the associated view
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListDto bidListDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "bidList/add";
        }
        bidListService.save(bidListDto);
        model.addAttribute("bidListDto", bidListService.getAll());
        logger.info("Bid saved in database");
        return "redirect:/bidList/list";
    }

    /**
     * Renders the view "bidList/update" with a prefilled form to update a bidList
     *
     * @param id    : The id of the bidList to be updated
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "bidList/update" to render the associated view
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidListDto", bidListService.getById(id));
        return "bidList/update";
    }

    /**
     * Redirects to the view "bidList/list" with the list of all bidList if the bidList updating succeed
     *
     * @param id         : The id of the bidList to be updated
     * @param bidListDto : The bidListDto with new values
     * @param result     : The binding results
     * @param model      : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/bidList/list" to redirect to the associated view if the operation succeeds, otherwise the string "bidList/update" to display the reason of the failure on the associated view
     */
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

    /**
     * Redirects to the view "bidList/list" with the list of all bidList if the bidList updating succeed
     *
     * @param id    : The id of the bidList to be deleted
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/bidList/list" to redirect to the associated view if the operation succeeds, otherwise the reason of the failure
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.delete(id);
        model.addAttribute("bidListDto", bidListService.getAll());
        logger.info(String.format("Bid %s deleted", id));
        return "redirect:/bidList/list";
    }
}
