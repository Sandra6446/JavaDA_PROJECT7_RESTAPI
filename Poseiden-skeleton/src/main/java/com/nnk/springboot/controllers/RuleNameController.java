package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.RuleNameDto;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Renders the view "ruleName/list" with the list of all ruleNames
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "ruleName/list" to render the associated view
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNameDto", ruleNameService.getAll());
        return "ruleName/list";
    }

    /**
     * Renders the view "ruleName/add" with a form to add a ruleName
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "ruleName/add" to render the associated view
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute(new RuleNameDto());
        return "ruleName/add";
    }

    /**
     * Redirects to the view "ruleName/list" with the list of all ruleNames if the ruleName saving succeed
     *
     * @param ruleNameDto : The ruleNameDto to create
     * @param result      : The binding results
     * @param model       : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/ruleName/list" to redirect to the associated view if the operation succeeds, otherwise the string "ruleName/add" to display the reason of the failure on the associated view
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameDto ruleNameDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "ruleName/add";
        }
        ruleNameService.save(ruleNameDto);
        model.addAttribute("ruleNameDto", ruleNameService.getAll());
        logger.info("RuleName saved in database");
        return "redirect:/ruleName/list";

    }

    /**
     * Renders the view "ruleName/update" with a prefilled form to update a ruleName
     *
     * @param id    : The id of the ruleName to be updated
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "ruleName/update" to render the associated view
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ruleNameDto", ruleNameService.getById(id));
        return "ruleName/update";
    }

    /**
     * Redirects to the view "ruleName/list" with the list of all ruleNames if the ruleName updating succeed
     *
     * @param id          : The id of the ruleName to be updated
     * @param ruleNameDto : The ruleNameDto with new values
     * @param result      : The binding results
     * @param model       : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/ruleName/list" to redirect to the associated view if the operation succeeds, otherwise the string "ruleName/update" to display the reason of the failure on the associated view
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDto ruleNameDto,
                                 BindingResult result, Model model) {
        ruleNameDto.setId(id);
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "ruleName/update";
        }
        ruleNameService.update(ruleNameDto);
        model.addAttribute("ruleNameDto", ruleNameService.getAll());
        logger.info(String.format("Rule %s updated", id));
        return "redirect:/ruleName/list";
    }

    /**
     * Redirects to the view "ruleName/list" with the list of all ruleNames if the ruleName updating succeed
     *
     * @param id    : The id of the ruleName to be deleted
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/ruleName/list" to redirect to the associated view if the operation succeeds, otherwise the reason of the failure
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(id);
        model.addAttribute("ruleNameDto", ruleNameService.getAll());
        logger.info(String.format("Rule %s deleted", id));
        return "redirect:/ruleName/list";
    }
}
