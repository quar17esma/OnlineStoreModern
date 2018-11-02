package com.quar17esma.controller;

import com.quar17esma.model.User;
import com.quar17esma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
@ComponentScan({"com.quar17esma.security"})
public class CommonController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        User user = userService.findByEmail(userController.getPrincipal());
        model.addAttribute("userFirstName", user.getFirstName());
        return "accessDenied";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
    public String showContactForm() {
        return "contact";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/stores"}, method = RequestMethod.GET)
    public String showStores() {
        return "stores";
    }

    @RequestMapping(value = {"/message"}, method = RequestMethod.GET)
    public String showMessage() {
        return "message";
    }
}
