package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    /**
     * Renders the view "home"
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "home" to render the associated view
     */
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    /**
     * Redirects to the view "bidList/list" with the list of all bidList
     *
     * @param model : The model map with data (attributes) to be transmitted to the view
     * @return The string "redirect:/bidList/list" to redirect to the associated view
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }

}
