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

    /**
     * Renders the view "user/list" with the list of all users
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "user/list" to render the associated view
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user/list";
    }

    /**
     * Renders the view "user/add" with a form to add a user
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "user/add" to render the associated view
     */
    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute(new UserDto());
        return "user/add";
    }

    /**
     * Redirects to the view "user/list" with the list of all users if the user saving succeed
     *
     * @param userDto : The userDto to create
     * @param result  : The binding results
     * @param model   : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/user/list" to redirect to the associated view if the operation succeeds, otherwise the string "user/add" to display the reason of the failure on the associated view
     */
    @PostMapping("/user/validate")
    public String validate(@Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error(result.getAllErrors());
            return "user/add";
        }
        userService.save(userDto);
        model.addAttribute("users", userService.getAll());
        logger.info("User saved in database");
        return "redirect:/user/list";
    }

    /**
     * Renders the view "user/update" with a prefilled form to update a user
     *
     * @param id    : The id of the user to be updated
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "user/update" to render the associated view
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("userDto", userService.getById(id));
        return "user/update";
    }

    /**
     * Redirects to the view "user/list" with the list of all users if the user updating succeed
     *
     * @param id      : The id of the user to be updated
     * @param userDto : The userDto with new values
     * @param result  : The binding results
     * @param model   : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/user/list" to redirect to the associated view if the operation succeeds, otherwise the string "user/update" to display the reason of the failure on the associated view
     */
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

    /**
     * Redirects to the view "user/list" with the list of all users if the user updating succeed
     *
     * @param id    : The id of the user to be deleted
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/user/list" to redirect to the associated view if the operation succeeds, otherwise the reason of the failure
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.delete(id);
        model.addAttribute("users", userService.getAll());
        logger.info(String.format("User %s deleted", id));
        return "redirect:/user/list";
    }
}
