package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.RuleNameDto;
import com.nnk.springboot.domain.entity.RuleName;
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

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNameDto", ruleNameService.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleNameDto ruleNameDto) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameDto ruleNameDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "ruleName/add";
        }
        ruleNameService.save(ruleNameDto);
        model.addAttribute("ruleNameDto", ruleNameService.getAll());
        logger.info(String.format("Rule %s saved in database", ruleNameDto.getId()));
        return "redirect:/ruleName/list";

    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ruleNameDto", ruleNameService.getById(id));
        return "ruleName/update";
    }

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

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(id);
        model.addAttribute("ruleNameDto", ruleNameService.getAll());
        logger.info(String.format("Rule %s deleted", id));
        return "redirect:/ruleName/list";
    }
}
