package com.konstantinov.springshop.controller;

import com.konstantinov.springshop.annotation.CurrentUser;
import com.konstantinov.springshop.exception.EmailExistsException;
import com.konstantinov.springshop.model.User;
import com.konstantinov.springshop.repository.UserRepository;
import com.konstantinov.springshop.service.UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PrincipleController {
    private static Logger LOG = LoggerFactory.getLogger(PrincipleController.class);

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/cabinet", method = RequestMethod.GET)
    public String currentUserName(@CurrentUser UserDetailService.UserPrincipal userDetails, Model model) {
        LOG.info(String.valueOf(userDetails.getUser()));
        User user = userDetails.getUser();
        LOG.info("Principle id: " + user.getId());
        model.addAttribute("principle", userRepository.findOne(user.getId()));
        return "cabinet";
    }

    @RequestMapping("principle/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws UsernameNotFoundException {
        model.addAttribute("principle", userRepository.findOne(id));
        LOG.info("Find user id before edit: " + id);
        return "principle/principleform";
    }

    @RequestMapping(value = "principle", method = RequestMethod.POST)
    public String updateUser(User user) throws UsernameNotFoundException, EmailExistsException {
        userRepository.save(user);
        LOG.info("Update user: " + user);
        return "redirect:/cabinet";
    }
}