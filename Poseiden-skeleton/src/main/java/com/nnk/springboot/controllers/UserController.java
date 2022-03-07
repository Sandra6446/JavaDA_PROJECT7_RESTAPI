package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.UserDto;
import com.nnk.springboot.service.UserService;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@NoArgsConstructor
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(UserDto userDto) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "user/add";
        }
        userService.save(userDto);
        model.addAttribute("users", userService.getAll());
        logger.info(String.format("User %s saved in database", userDto.getId()));
        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("userDto", userService.getById(id));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDto userDto,
                             BindingResult result, Model model) {
        userDto.setId(id);
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "user/update";
        }
        userService.update(userDto);
        model.addAttribute("users", userService.getAll());
        logger.info(String.format("User %s updated", id));
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.delete(id);
        model.addAttribute("users", userService.getAll());
        logger.info(String.format("User %s deleted", id));
        return "redirect:/user/list";
    }
}
