package com.konstantinov.springshop.controllers;

import com.konstantinov.springshop.exception.EmailExistsException;
import com.konstantinov.springshop.models.User;
import com.konstantinov.springshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SingUpController {
    private static Logger LOG = LoggerFactory.getLogger(SingUpController.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("registration")
    public String newProduct(Model model) {
        model.addAttribute("user", new User());
        return "singUp/registration";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveUser(User user) throws UsernameNotFoundException, EmailExistsException {
        userService.registerNewUserAccount(user);
        LOG.info("Save user: " + user);
        return "redirect:/user/" + user.getId();
    }
}
