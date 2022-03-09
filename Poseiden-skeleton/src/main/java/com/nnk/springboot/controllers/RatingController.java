package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.RatingDto;
import com.nnk.springboot.service.RatingService;
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
public class RatingController {

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @Autowired
    RatingService ratingService;

    /**
     * Renders the view "rating/list" with the list of all ratings
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "rating/list" to render the associated view
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratingDto", ratingService.getAll());
        return "rating/list";
    }

    /**
     * Renders the view "rating/add" with a form to add a rating
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "rating/add" to render the associated view
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute(new RatingDto());
        return "rating/add";
    }

    /**
     * Redirects to the view "rating/list" with the list of all ratings if the rating saving succeed
     *
     * @param ratingDto : The ratingDto to create
     * @param result    : The binding results
     * @param model     : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/rating/list" to redirect to the associated view if the operation succeeds, otherwise the string "rating/add" to display the reason of the failure on the associated view
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDto ratingDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "rating/add";
        }
        ratingService.save(ratingDto);
        model.addAttribute("ratingDto", ratingService.getAll());
        logger.info("Rating saved in database");
        return "redirect:/rating/list";
    }

    /**
     * Renders the view "rating/update" with a prefilled form to update a rating
     *
     * @param id    : The id of the rating to be updated
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "rating/update" to render the associated view
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ratingDto", ratingService.getById(id));
        return "rating/update";
    }

    /**
     * Redirects to the view "rating/list" with the list of all ratings if the rating updating succeed
     *
     * @param id        : The id of the rating to be updated
     * @param ratingDto : The ratingDto with new values
     * @param result    : The binding results
     * @param model     : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/rating/list" to redirect to the associated view if the operation succeeds, otherwise the string "rating/update" to display the reason of the failure on the associated view
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDto ratingDto,
                               BindingResult result, Model model) {
        ratingDto.setId(id);
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "rating/update";
        }
        ratingService.update(ratingDto);
        model.addAttribute("ratingDto", ratingService.getAll());
        logger.info(String.format("Rating %s updated", id));
        return "redirect:/rating/list";
    }

    /**
     * Redirects to the view "rating/list" with the list of all ratings if the rating updating succeed
     *
     * @param id    : The id of the rating to be deleted
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/rating/list" to redirect to the associated view if the operation succeeds, otherwise the reason of the failure
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.delete(id);
        model.addAttribute("ratingDto", ratingService.getAll());
        logger.info(String.format("Rating %s deleted", id));
        return "redirect:/rating/list";
    }
}
